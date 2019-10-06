package com.sevenpeakssoftware.cars.repository

import com.sevenpeakssoftware.cars.api.*
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDao
import com.sevenpeakssoftware.cars.testing.OpenForTesting
import io.reactivex.Flowable
import io.reactivex.BackpressureStrategy
import javax.inject.Inject


@OpenForTesting
class ArticlesRepo @Inject constructor(
    private val dao: ArticleDao,
    private val api: ArticleApiService ) {

    fun loadArticles(): Flowable<Resource<List<Article>>> {

        return Flowable.create({ emitter ->
            object : NetworkBoundSource<List<Article>, ArticlesApiResponse>(emitter) {

                override fun createCall() = api.getArticles()

                override fun loadFromDb() = dao.loadArticles()

                override fun saveCallResult(data: ArticlesApiResponse) {
                    dao.insertArticles(data.content)
                }
            }

        }, BackpressureStrategy.BUFFER)

    }

}