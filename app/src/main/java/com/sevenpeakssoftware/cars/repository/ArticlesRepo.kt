package com.sevenpeakssoftware.cars.repository

import com.sevenpeakssoftware.cars.data.Article
import io.reactivex.Flowable

interface ArticlesRepo {

    fun loadArticles(): Flowable<Resource<List<Article>>>
}