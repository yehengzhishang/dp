package org.yu.zz.dp

import java.util.*


/**
 * 双链表 完成浏览器 前进，后退的操作
 */
class Browser {
    private val mStackFlow: Deque<String> = ArrayDeque()
    private val mStackAux: Deque<String> = ArrayDeque()

    fun click(page: String) {
        mStackFlow.push(page)
        mStackAux.clear()
    }

    fun back() {
        if (mStackFlow.size < 2) {
            return
        }
        val page = mStackFlow.pop()
        mStackAux.push(page)
    }

    fun front() {
        if (mStackAux.size < 1) {
            return
        }
        val page = mStackAux.pop()
        mStackFlow.push(page)
    }
}