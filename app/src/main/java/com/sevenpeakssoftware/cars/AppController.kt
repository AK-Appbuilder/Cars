package com.sevenpeakssoftware.cars

import android.app.Activity
import com.jakewharton.threetenabp.AndroidThreeTen
import com.sevenpeakssoftware.cars.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject


open class AppController : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }


}