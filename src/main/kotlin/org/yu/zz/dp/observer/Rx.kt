package org.yu.zz.dp.observer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *  rx 主要实现
 *  只保留 最基本的onNext
 *  当前实现 just map subscribeOn ObserverOn,自创Pair操作符
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
        // 原方法非空安全，线程校验，已知忽略
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

    fun subscribeOn(executorService: ExecutorService): Observable<T> {
        return ObservableSubscribeOn(this, executorService)
    }

    fun observerOn(executorService: ExecutorService): Observable<T> {
        return ObservableObserverOn(this, executorService)
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

//      <editor-fold desc="subscribeOn">
class SubscribeOnRunnable<T>(private val upstream: ObservableSource<T>, private val observer: Observer<T>) : Runnable {
    override fun run() {
        println("subscribe thread == ${Thread.currentThread().id}")
        upstream.subscribe(observer)
    }
}

class ObservableSubscribeOn<T>(private val upstream: ObservableSource<T>, private val executorService: ExecutorService) : Observable<T>() {
    override fun subscribeActual(observer: Observer<T>) {
        executorService.execute(SubscribeOnRunnable(upstream, observer))
    }
}
//      </editor-fold desc="subscribeOn">

// <editor-fold desc="ObserverOn">
class ObserverOnObserver<T>(private val executorService: ExecutorService, private val observer: Observer<T>) : Observer<T> {
    override fun onNext(t: T) {
        executorService.execute { observer.onNext(t) }
    }
}

class ObservableObserverOn<T>(private val upstream: ObservableSource<T>, private val executorService: ExecutorService) : Observable<T>() {
    override fun subscribeActual(observer: Observer<T>) {
        upstream.subscribe(ObserverOnObserver(executorService, observer))
    }
}
// </editor-fold desc="ObserverOn">

fun main(args: Array<String>) {
    println("main 方法线程 == ${Thread.currentThread().id}")
    Observable.just(1)
            .map(object : Function<Int, String> {
                override fun apply(t: Int): String {
                    println("map 方法线程 == ${Thread.currentThread().id}")
                    return "$t === map"
                }
            })
            .pair(object : Function<String, ObservableSource<Int>> {
                override fun apply(t: String): ObservableSource<Int> {
                    // 这里应该是延迟请求，类似retrofit网络请求那种
                    return ObservableJust(t.length)
                }
            })
            .subscribeOn(Executors.newCachedThreadPool())
            // 不能切原线程原因是没有相应的looper (安卓相关)
            .observerOn(Executors.newCachedThreadPool())
            .subscribe(object : Observer<Pair<String, Int>> {
                override fun onNext(t: Pair<String, Int>) {
                    println("onNext 方法线程 == ${Thread.currentThread().id}")
                    println("${t.first}的长度是${t.second}")
                }
            })
}