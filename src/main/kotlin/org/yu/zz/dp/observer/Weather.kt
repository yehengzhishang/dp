package org.yu.zz.dp.observer

fun main(args: Array<String>) {
    val view = View()
    val clickListener = TallyListener()
    view.registerObserver(clickListener)
    view.performClick()
}

interface WeatherObserver<S> {
    fun update(s: S)
}

class Wrapper<O> {
    var o: O? = null
}

interface Subject<OD, O : WeatherObserver<OD>> {
    val w: Wrapper<O>

    fun registerObserver(o: O) {
        w.o = o
    }

    fun removeObserver(s: O) {
        w.o = null
    }

    fun notifyObserver(od: OD) {
        w.o?.update(od)
    }


}

interface SubSelf<O> : Subject<O, WeatherObserver<O>>

interface ObserverList<S> : WeatherObserver<S> {
    val list: MutableList<WeatherObserver<S>>
    override fun update(s: S) {
        for (observer in list) {
            observer.update(s)
        }
    }


}

class View : SubSelf<View> {
    override val w: Wrapper<WeatherObserver<View>> = Wrapper()
    fun performClick() {
        notifyObserver(this)
    }
}

interface OnClickListener : WeatherObserver<View>

class TallyListener : OnClickListener {
    private var cnt = 0;
    override fun update(s: View) {
        cnt++
        println("View click count : $cnt")
    }
}
