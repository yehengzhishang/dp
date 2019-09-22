package org.yu.zz.dp.factory.abs

interface Item {
    val caption: String
    fun makeHtml()
}

interface Link : Item {
    val url: String
}

interface Tray : Item {
    val items: MutableList<Item>

    fun addItem(item: Item) {
        items.add(item)
    }
}

interface ItemFactory {
}