package org.yu.zz.dp.factory

fun main(args: Array<String>) {
    val pizzaStore = DefaultPizzaStore()
    pizzaStore.orderPizza("default")
}

open class Pizza {
    fun prepare() {
        println("pizza prepare")
    }

    fun cut() {
        println("pizza cut")
    }

    fun bake() {
        println("pizza bake")
    }

    fun box() {
        println("pizza box")
    }
}

abstract class PizzaStore {
    fun orderPizza(pizzaType: String) {
        // 简单工厂模式
//        val pizza = SimplePizzaFactory().createPizza(pizzaType)
        //工厂模式
        val pizza = createPizza(pizzaType)
        pizza.prepare()
        pizza.bake()
        pizza.cut()
        pizza.box()
    }

    abstract fun createPizza(pizzaType: String): Pizza
}


class SimplePizzaFactory {
    fun createPizza(pizzaType: String): Pizza {
        return when (pizzaType) {
            "cheese" -> CheesePizza()
            else -> Pizza()
        }
    }
}

class DefaultPizzaStore : PizzaStore() {
    override fun createPizza(pizzaType: String): Pizza = Pizza()
}

class CheesePizza : Pizza()