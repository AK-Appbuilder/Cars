package com.sevenpeakssoftware.cars.di

import com.sevenpeakssoftware.cars.ui.CarsFeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
     abstract fun contributeCarsFragment(): CarsFeedFragment

}