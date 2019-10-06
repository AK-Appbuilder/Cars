package com.sevenpeakssoftware.cars.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sevenpeakssoftware.cars.api.ArticleApiService
import com.sevenpeakssoftware.cars.api.ArticlesApiResponse
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDao
import com.sevenpeakssoftware.cars.utils.mock
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*


@RunWith(JUnit4::class)
class ArticleRepoTest {

    private lateinit var repository: ArticlesRepo

    private val dao = Mockito.mock(ArticleDao::class.java)

    private val service = Mockito.mock(ArticleApiService::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun init() {
        repository = ArticlesRepo(dao, service)

        `when`(dao.loadArticles()).thenReturn(Flowable.just(listOf(createArticle())))

        `when`(service.getArticles()).thenReturn(Single.just(ArticlesApiResponse("",listOf(createArticle()))))
    }


    @Test
    fun verifyRemoteAndLocalSource_methodsCalled(){
        repository.loadArticles().subscribe {  }

        verify(dao, times(2)).loadArticles()
        verify(service).getArticles()
        verify(dao).insertArticles(ArgumentMatchers.anyList())
    }
    


    private fun createArticle(): Article {

        return Article(
            1, "Audi-Greatest Start", "", "11-05-2018",
            emptyList(), "", emptyList(), 20343, 3534
        )

    }



}