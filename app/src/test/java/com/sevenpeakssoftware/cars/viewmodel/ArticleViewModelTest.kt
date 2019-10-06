package com.sevenpeakssoftware.cars.viewmodel

import androidx.annotation.NonNull
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.repository.ArticlesRepo
import com.sevenpeakssoftware.cars.repository.Resource
import com.sevenpeakssoftware.cars.utils.LiveDataTestUtil
import com.sevenpeakssoftware.cars.utils.mock
import io.reactivex.BackpressureStrategy
import io.reactivex.Emitter
import io.reactivex.Flowable
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@RunWith(JUnit4::class)
class ArticleViewModelTest {

    // under test
    private lateinit var articleViewModel: ArticleViewModel

    private lateinit var resource: Resource<out List<Article>>

    private var articleRepo = mock(ArticlesRepo::class.java)

    @Rule
    @JvmField
    internal var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        val listOf = listOf(createArticle(), createArticle())
        resource = Resource.success(listOf)

        articleViewModel = ArticleViewModel(articleRepo)

        `when`(articleRepo.loadArticles()).thenReturn(Flowable.just(resource))
    }


    @Test
    fun verifyRepoLoadArticlesCall() {

        articleViewModel.articlesResource.observeForever(mock())

        verify(articleRepo).loadArticles()
    }


    @Test
    fun loadArticlesFromRepo_LoadingTogglesAndDataLoaded() {
        articleViewModel.articlesResource.observeForever(mock())

        assertThat(LiveDataTestUtil.getValue(articleViewModel.dataLoading)).isFalse()

        assertThat(LiveDataTestUtil.getValue(articleViewModel.articlesResource).data).hasSize(2)
    }


    @Test
    fun refresh_loadArticleFromRepo() {
        articleViewModel.articlesResource.observeForever(mock())

        articleViewModel.refresh()

        verify(articleRepo, times(2)).loadArticles()
    }

    private fun createArticle(): Article {

        return Article(
            1, "Audi-Greatest Start", "", "11-05-2018",
            emptyList(), "", emptyList(), 20343, 3534
        )

    }

}