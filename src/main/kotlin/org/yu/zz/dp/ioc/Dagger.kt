package org.yu.zz.dp.ioc

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject


data class Dagger constructor(val inject: String)

class DaggerWrapper {
    @Inject
    lateinit var mDagger: Dagger

    fun startInject() {
        DaggerDaggerComponent.builder()
                .rainModule(RainModule())
                .nameModule(NameModule())
                .build()
                .inject(this)
    }

    fun showInject() {
        println(mDagger.toString())
    }
}

@Module
class NameModule {
    @Provides
    fun provideName(): String {
        return "summer rain"
    }
}

@Module(includes = [NameModule::class])
class RainModule {
    @Provides
    fun provideDagger(name: String): Dagger {
        return Dagger(name)
    }
}

@Component(modules = [RainModule::class])
interface DaggerComponent {
    fun inject(wrapper: DaggerWrapper)
}


fun main(args: Array<String>) {
    val wrapper = DaggerWrapper()
    wrapper.startInject()
    wrapper.showInject()
}
