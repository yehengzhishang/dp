package org.yu.zz.dp.observer

/**
 *  rx 主要实现
 *  只保留 最基本的onNext
 *  当前实现 just map ,自创Pair操作符
 *
 * 已知忽略内容 PECS
 * DISABLE
 * 线程安全
 * 非空校验
 * 全局监听
 */
interface Observer<T> {
    fun onNext(t: T)
}

interface ObservableSource<T> {
    fun subscribe(observer: Observer<T>)
}

abstract class Observable<T> : ObservableSource<T> {
    override fun subscribe(observer: Observer<T>) {
        // 原方法有种安全，线程校验，已知忽略
        subscribeActual(observer)
    }

    companion object {
        // 原为静态方法
        fun <T> just(source: T): Observable<T> {
            return ObservableJust(source)
        }
    }

    abstract fun subscribeActual(observer: Observer<T>)

    fun <R> map(mapper: Function<T, R>): Observable<R> {
        return ObservableMap<T, R>(this, mapper)
    }
}

// 自创操作符 模仿真实情况，写kt扩展方法
fun <T, R> Observable<T>.pair(mapper: Function<T, ObservableSource<R>>): Observable<Pair<T, R>> {
    return ObservablePair(this, mapper)
}

class ObservableJust<T> constructor(private val source: T) : Observable<T>() {
    override fun subscribeActual(observer: Observer<T>) {
        observer.onNext(source)
    }
}

interface Function<T, R> {
    fun apply(t: T): R
}

class MapObserver<T, R>(private val observer: Observer<T>, private val mapper: Function<R, T>) : Observer<R> {
    override fun onNext(t: R) {
        val down = mapper.apply(t)
        observer.onNext(down)
    }
}

class ObservableMap<T, R>(private val upstream: ObservableSource<T>, private val mapper: Function<T, R>) : Observable<R>() {
    override fun subscribeActual(observer: Observer<R>) {
        upstream.subscribe(MapObserver(observer, mapper))
    }
}

class InnerPairObserver<T, R> constructor(private val source: T, private val observer: Observer<Pair<T, R>>) : Observer<R> {
    override fun onNext(t: R) {
        observer.onNext(Pair(source, t))
    }
}

class PairObserver<T, R> constructor(private val observer: Observer<Pair<T, R>>, private val mapper: Function<T, ObservableSource<R>>) : Observer<T> {
    override fun onNext(t: T) {
        val p = mapper.apply(t)
        p.subscribe(InnerPairObserver(t, observer))
    }
}


class ObservablePair<T, R> constructor(private val upstream: ObservableSource<T>, private val mapper: Function<T, ObservableSource<R>>) : Observable<Pair<T, R>>() {
    override fun subscribeActual(observer: Observer<Pair<T, R>>) {
        upstream.subscribe(PairObserver(observer, mapper))
    }
}

fun main(args: Array<String>) {
    Observable.just(1)
            .map(object : Function<Int, String> {
                override fun apply(t: Int): String {
                    return "$t === map"
                }
            })
            .pair(object : Function<String, ObservableSource<Int>> {
                override fun apply(t: String): ObservableSource<Int> {
                    // 这里应该是延迟请求，类似retrofit网络请求那种
                    return ObservableJust(t.length)
                }
            })
            .subscribe(object : Observer<Pair<String, Int>> {
                override fun onNext(t: Pair<String, Int>) {
                    println("${t.first}的长度是${t.second}")
                }
            })
}