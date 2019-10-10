package com.sevenpeakssoftware.cars.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.sevenpeakssoftware.cars.api.ArticleApiService
import com.sevenpeakssoftware.cars.api.ArticlesApiResponse
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDao
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


@RunWith(JUnit4::class)
class ArticleRepoTest {

    private lateinit var repository: DefaultArticlesRepo

    private val dao = mock(ArticleDao::class.java)

    private val service = mock(ArticleApiService::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()



    @Before
    fun init() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        repository = DefaultArticlesRepo(dao, service)

        `when`(dao.loadArticles()).thenReturn(Flowable.just(listOf(createArticle())))

        `when`(service.getArticles()).thenReturn(Single.just(ArticlesApiResponse("",listOf(createArticle()))))
    }


    @Test
    fun verifyRemoteAndLocalSource_methodsCalls() {

        repository.loadArticles().subscribe {  }

        verify(dao, times(2)).loadArticles()
        verify(service).getArticles()
        verify(dao).insertArticles(ArgumentMatchers.anyList())
    }

    @Test
    fun loadingArticles_in_subscribeMethod() {

        val subscriber = object : Subscriber<Resource<List<Article>>> {
            override fun onComplete() {
            }

            override fun onSubscribe(s: Subscription?) {
            }

            override fun onNext(it: Resource<List<Article>>?) {
                Truth.assertThat(it?.data).isNotEmpty()
            }

            override fun onError(t: Throwable?) {
            }
        }
        repository.loadArticles().subscribe(subscriber)
    }


    private fun createArticle(): Article {
        return Article(
            1, "Audi-Greatest Start", "", "11-05-2018",
            emptyList(), "", emptyList(), 20343, 3534
        )
    }
}