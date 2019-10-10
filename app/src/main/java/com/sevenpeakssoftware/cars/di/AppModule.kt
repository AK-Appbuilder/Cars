package com.sevenpeakssoftware.cars.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.sevenpeakssoftware.cars.api.ArticleApiService
import com.sevenpeakssoftware.cars.db.ArticleDao
import com.sevenpeakssoftware.cars.db.ArticleDb
import com.sevenpeakssoftware.cars.repository.ArticlesRepo
import com.sevenpeakssoftware.cars.repository.DefaultArticlesRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [AppModule.ApplicationModuleBinds::class])
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
    fun provideDb(context: Context): ArticleDb {
        return Room
            .databaseBuilder(context, ArticleDb::class.java, "article.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDb): ArticleDao {
        return db.articleDao()
    }

    @Module
    abstract class ApplicationModuleBinds {

        @Singleton
        @Binds
        abstract fun bindRepository(repo: DefaultArticlesRepo): ArticlesRepo

    }
}