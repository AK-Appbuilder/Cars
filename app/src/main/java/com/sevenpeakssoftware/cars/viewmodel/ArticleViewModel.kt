package com.sevenpeakssoftware.cars.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.sevenpeakssoftware.cars.api.ArticleService
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.db.ArticleDb
import com.sevenpeakssoftware.cars.repository.ArticlesRepo
import com.sevenpeakssoftware.cars.repository.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleViewModel (application: Application): AndroidViewModel(application) {


    private val repo: ArticlesRepo =
        ArticlesRepo(ArticleDb.create(getApplication()).articleDao(), ArticleService.create())


    private val genreLiveData: LiveData<Resource<List<Article>>>



     init {
             genreLiveData = LiveDataReactiveStreams.fromPublisher(
                 repo.loadArticles()
                 .subscribeOn(Schedulers.newThread())
                 .observeOn(AndroidSchedulers.mainThread()))
         }


    fun getArticles(): LiveData<Resource<List<Article>>> {
        return genreLiveData
    }

}