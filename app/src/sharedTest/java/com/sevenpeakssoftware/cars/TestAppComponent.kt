package com.sevenpeakssoftware.cars
import android.content.Context
import com.sevenpeakssoftware.cars.di.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        FragmentModule::class]
)
interface TestAppComponent  : AndroidInjector<TestAppController> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestAppComponent
    }
}