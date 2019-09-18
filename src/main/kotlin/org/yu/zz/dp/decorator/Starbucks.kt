package org.yu.zz.dp.decorator


interface Beverage {
    fun description(): String
    fun coast(): Float
}

abstract class CondimentDecorator(private val beverage: Beverage?) : Beverage {

    fun getBeverageCoast(): Float = beverage?.coast() ?: 0F
}

class Espresso : Beverage {
    override fun coast(): Float {
        return 0.9F
    }

    override fun description(): String {
        return "浓缩咖啡"
    }
}

class HouseBlend : Beverage {
    override fun description(): String {
        return "混合咖啡"
    }

    override fun coast(): Float {
        return 0.89F
    }
}

class Mocha(beverage: Beverage?) : CondimentDecorator(beverage) {
    override fun description(): String = "摩卡"

    override fun coast(): Float = (0.20 + getBeverageCoast()).toFloat()
}