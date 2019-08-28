package com.sevenpeakssoftware.cars.repository

import com.sevenpeakssoftware.cars.api.*
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDao
import io.reactivex.Flowable
import io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.functions.Function


class ArticlesRepo(
    private val dao: ArticleDao,
    private val api: ArticleService ) {


    fun loadArticles(): Flowable<Resource<List<Article>>> {

        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<Article>, ArticlesResponse>(emitter) {

                override fun createCall() = api.getArticles()

                override fun loadFromDb() = dao.loadArticles()

                override fun saveCallResult(data: ArticlesResponse) {
                    dao.insertArticles(data.content)
                }
            }

        }, BackpressureStrategy.BUFFER)
    }

}