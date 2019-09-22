package org.yu.zz.dp.chain

import java.util.concurrent.ThreadLocalRandom

//自己的想法，看看就好
fun main(args: Array<String>) {
    val manager = ChainManager()
    manager.addHandle(LowChainHandler())
    manager.addHandle(MediumHandler())
    manager.addHandle(HighHandler())

    val targetChain = TestChain()
    targetChain.handler = manager

    targetChain.test(30)
}

class TestChain {
    var handler: IChainHandle? = null
    fun test(num: Int) {
        for (i in 0..num) {
            val level = getLevel()
            println("test num : $i ,level is $level")
            handler?.chain(Chain(getLevel()))
        }
    }

    private fun getLevel(): Int = ThreadLocalRandom.current().nextInt(3)
}

data class Chain(val level: Int) {
    companion object {
        const val LEVEL_LOW = 0
        const val LEVEL_MEDIUM = 1
        const val LEVEL_HIGH = 2
    }
}

interface IChainHandle {
    fun chain(chain: Chain)
}

abstract class ChainHandle : IChainHandle

class ChainManager : ChainHandle() {
    private val listChain: MutableList<IChainHandle> = ArrayList()
    override fun chain(chain: Chain) {
        listChain.forEach {
            it.chain(chain)
        }
    }

    fun addHandle(handle: IChainHandle) = listChain.add(handle)
    fun removeHandle(handle: IChainHandle) = listChain.remove(handle)
}

class LowChainHandler : ChainHandle() {
    override fun chain(chain: Chain) {
        val level = chain.level
        if (level > Chain.LEVEL_LOW) {
            println("low Handler:too high to deal ")
        } else {
            println("low handler :  ing")
        }
    }
}

class MediumHandler : ChainHandle() {
    override fun chain(chain: Chain) {
        val level = chain.level
        when {
            level < Chain.LEVEL_MEDIUM -> println("medium handler : too low to deal")
            level > Chain.LEVEL_MEDIUM -> println("medium handler : too high to deal")
            else -> println("medium handler : ing")
        }
    }
}

class HighHandler : ChainHandle() {
    override fun chain(chain: Chain) {
        val level = chain.level
        when {
            level < Chain.LEVEL_HIGH -> println("high handler : too low to deal")
            else -> println("high handler : ing")
        }
    }
}