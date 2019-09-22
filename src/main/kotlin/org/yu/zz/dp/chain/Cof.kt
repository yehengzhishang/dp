package org.yu.zz.dp.chain

fun main(args: Array<String>) {
    val low = LowLevelHandler("low")
    val high = HighLevelHandler()
    val terminator = TerminatorHandler("TerminatorHandler")
    low.next = high
    high.next = terminator

    low.handleRequest(Request(1, "1", 3))
}

data class Request(var type: Int, var id: String, var level: Int)

// 书上的例子 start
abstract class RequestHandle(name: String) {
    var name: String? = name
        private set
    var next: RequestHandle? = null


    fun handleRequest(request: Request) {
        when {
            handleInner(request) -> accept()
            next != null -> next!!.handleRequest(request)
            else -> println("没有响应")
        }
    }

    abstract fun handleInner(request: Request): Boolean

    open fun accept() {
        val p = name + "已经处理request"
        println(p)
    }
}

class LowLevelHandler(lowName: String) : RequestHandle(name = lowName) {
    override fun handleInner(request: Request): Boolean = request.level <= 0
}

class HighLevelHandler : RequestHandle(name = "highName") {
    override fun handleInner(request: Request): Boolean = request.level == 3
}

class TerminatorHandler(name: String) : RequestHandle(name = name) {
    override fun handleInner(request: Request): Boolean = true
}