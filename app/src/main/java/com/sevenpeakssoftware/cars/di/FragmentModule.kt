package com.sevenpeakssoftware.cars.di

import androidx.lifecycle.ViewModel
import com.sevenpeakssoftware.cars.ui.CarsFeedFragment
import com.sevenpeakssoftware.cars.viewmodel.ArticleViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [
        ViewModelModule::class
    ])
     abstract fun contributeCarsFragment(): CarsFeedFragment


    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    abstract fun bindUserViewModel(viewModel: ArticleViewModel): ViewModel


}