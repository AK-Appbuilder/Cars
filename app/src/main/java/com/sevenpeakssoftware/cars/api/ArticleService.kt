package com.sevenpeakssoftware.cars.api

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ArticleService {

    @GET("article/get_articles_list")
    fun getArticles(): Single<ArticlesResponse>


    companion object {
        private const val BASE_URL = "https://www.apphusetreach.no/application/119267/"

        fun create(): ArticleService = create(HttpUrl.parse(BASE_URL)!!)


        fun create(httpUrl: HttpUrl): ArticleService {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ArticleService::class.java)
        }
    }

}