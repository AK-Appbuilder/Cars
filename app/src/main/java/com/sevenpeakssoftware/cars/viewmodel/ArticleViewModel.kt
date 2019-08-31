package com.sevenpeakssoftware.cars.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sevenpeakssoftware.cars.api.ArticleApiService
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDb
import com.sevenpeakssoftware.cars.repository.ArticlesRepo
import com.sevenpeakssoftware.cars.repository.Resource
import com.sevenpeakssoftware.cars.repository.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleViewModel @Inject constructor( val repo: ArticlesRepo) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val loadFreshData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadFreshData.value = true
    }

    var articlesResource: LiveData<Resource<List<Article>>> = Transformations
        .switchMap(loadFreshData) { input ->
            LiveDataReactiveStreams.fromPublisher(
                repo.loadArticles()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        _dataLoading.value = it.status == Status.LOADING
                    }
            )
        }

    fun refresh() {
        loadFreshData.value = !(loadFreshData.value)!!
    }

}