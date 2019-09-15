package org.yu.zz.dp.strategy

fun main(args: Array<String>) {
    val duck = Duck()
    duck.fly = DefaultDuckFly()
    duck.performFly()
    duck.fly = RocketFly()
    duck.performFly()
}

// 组合 优于 继承
open class Duck {
    var fly: FlyBehaviour? = null

    fun performFly() {
        fly?.fly()
    }
}

//将 飞行 定义成 一种 行为
interface FlyBehaviour {
    fun fly()
}

class DefaultDuckFly : FlyBehaviour {
    override fun fly() {
        println("duck cant fly")
    }
}

class RocketFly : FlyBehaviour {
    override fun fly() {
        println("fly with a rocket")
    }
}