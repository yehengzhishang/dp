/**
 *   单例,只有一个类的单类真是看不出来啥效果
 */
fun main(args: Array<String>) {
    UserManager.INSTANCE.update("88888")
}

class User(var id: String) {
    var name = ""
}

class UserManager private constructor() {
    val NO_ID = -1
    var user: User = User((NO_ID.toString()))
    companion object {
        val INSTANCE: UserManager = UserManager()
    }

    fun update(id: String) {
        user = User(id)
    }
}

