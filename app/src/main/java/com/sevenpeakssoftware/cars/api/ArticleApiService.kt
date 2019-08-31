package com.sevenpeakssoftware.cars.api

import io.reactivex.Single
import retrofit2.http.GET

interface ArticleApiService {

    @GET("article/get_articles_list")
    fun getArticles(): Single<ArticlesApiResponse>

}