package com.sevenpeakssoftware.cars.repository

import com.sevenpeakssoftware.cars.data.Article
import io.reactivex.Flowable

class FakeRepository : ArticlesRepo {

    override fun loadArticles(): Flowable<Resource<List<Article>>> {
        val listOf = listOf(createArticle())
        var resource = Resource.success(listOf)

        return Flowable.just(resource)
    }

    private fun createArticle(): Article {
        return Article(
            1, "Audi-Greatest Start", "", "29.11.2017 15:12",
            emptyList(), "", emptyList(), 20343, 3534
        )
    }
}