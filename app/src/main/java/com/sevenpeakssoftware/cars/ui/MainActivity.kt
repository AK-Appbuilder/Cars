package com.sevenpeakssoftware.cars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.repository.Resource
import com.sevenpeakssoftware.cars.viewmodel.ArticleViewModel


class MainActivity : AppCompatActivity() {


    lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sevenpeakssoftware.cars.R.layout.activity_main)



        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        viewModel.getArticles().observe(this, Observer<Resource<List<Article>>> {

            Log.d("MainActivity", Gson().toJson(it.data))

//            Log.d("MainActivity", it?.message)
        })
    }
}
