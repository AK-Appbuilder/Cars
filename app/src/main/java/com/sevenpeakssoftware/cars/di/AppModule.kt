package com.sevenpeakssoftware.cars.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.sevenpeakssoftware.cars.api.ArticleApiService
import com.sevenpeakssoftware.cars.db.ArticleDao
import com.sevenpeakssoftware.cars.db.ArticleDb
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    private  val BASE_URL = "https://www.apphusetreach.no/application/119267/"

    @Singleton
    @Provides
    fun provideArticleApiService(): ArticleApiService {

        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(HttpUrl.parse(BASE_URL)!!)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): ArticleDb {
        return Room
            .databaseBuilder(app, ArticleDb::class.java, "article.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDb): ArticleDao {
        return db.articleDao()
    }

}