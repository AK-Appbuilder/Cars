package com.sevenpeakssoftware.cars
import com.sevenpeakssoftware.cars.repository.ArticlesRepo
import com.sevenpeakssoftware.cars.repository.FakeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TestAppModule {


    @Singleton
    @Provides
    fun provideMockRepository(): ArticlesRepo {
        return FakeRepository()
    }



}