package com.sevenpeakssoftware.cars.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.cars.viewmodel.ArticleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleViewModel::class)
    abstract fun bindUserViewModel(viewModel: ArticleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
