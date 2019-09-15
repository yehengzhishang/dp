package org.zz.dp.chainOfResponsibility

import org.zz.dp.chainOfResponsibility.TouchEvent.Companion.ACTION_UP

//安卓View中的事件分发，据说很像责任链
fun main(args: Array<String>) {
    val viewText = TextView("text")
    val viewVisibility = VisibilityView()
    viewVisibility.visibility = true
    val viewClick = ClickView()

    val layoutClick = ClickLayout()
    val layoutParent = SuperLayout()

    layoutClick.addView(viewText)
    layoutClick.addView(viewVisibility)
    layoutClick.addView(viewClick)

    layoutParent.addView(layoutClick)

    layoutParent.dispatchTouchEvent(TouchEvent(ACTION_UP))
}

data class TouchEvent(val action: Int) {
    companion object {
        const val ACTION_UP = 1
    }
}

open class View(val id: Int) {
    open fun dispatchTouchEvent(event: TouchEvent): Boolean {
        println("id : $id == dispatch")
        return onTouchEvent(event)
    }

    open fun onTouchEvent(event: TouchEvent): Boolean {
        println("id : $id == ======== ========= touch")
        return false
    }
}

open class ViewGroup(id: Int) : View(id = id) {
    private val listChildren: MutableList<View> = ArrayList()
    override fun dispatchTouchEvent(event: TouchEvent): Boolean {
        println("id : $id == dispatch")
        if (onInterceptTouchEvent(event)) {
            return true
        }
        return super.dispatchTouchEvent(event)
    }

    fun addView(view: View) = listChildren.add(view)

    open fun onInterceptTouchEvent(event: TouchEvent): Boolean {
        println("id : $id == ======== intercept")
        listChildren.forEach {
            if (it.dispatchTouchEvent(event)) {
                return@onInterceptTouchEvent true
            }
        }
        return false
    }
}


class SuperLayout : ViewGroup(0) {
    override fun onTouchEvent(event: TouchEvent): Boolean {
        return true
    }
}

class ClickLayout : ViewGroup(1) {
    var click: Unit? = null

    override fun onTouchEvent(event: TouchEvent): Boolean {
        return click != null
    }
}

class TextView(var text: String) : View(2)

class VisibilityView : View(id = 3) {
    var visibility = false

    override fun onTouchEvent(event: TouchEvent): Boolean {
        return visibility
    }
}

class ClickView : View(4) {
    var click: Unit? = null
    override fun onTouchEvent(event: TouchEvent): Boolean {
        return click != null
    }
}