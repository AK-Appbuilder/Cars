package com.sevenpeakssoftware.cars
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DaggerTestApplicationRule : TestWatcher() {

    lateinit var component: TestAppComponent
        private set

    override fun starting(description: Description?) {
        super.starting(description)

        val app = ApplicationProvider.getApplicationContext<Context>() as TestAppController



        component = DaggerTestAppComponent.factory().create(app)
        component.inject(app)

    }
}