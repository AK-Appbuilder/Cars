package com.sevenpeakssoftware.cars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.cars.databinding.FragmentCarsFeedBinding
import com.sevenpeakssoftware.cars.viewmodel.ArticleViewModel
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class CarsFeedFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

   val viewModel by viewModels<ArticleViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentCarsFeedBinding

    private lateinit var articlesAdapter: ArticlesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCarsFeedBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupArticlesAdapter()
    }

    private fun setupArticlesAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            articlesAdapter = ArticlesAdapter()
            viewDataBinding.articlesList.adapter = articlesAdapter
        } else {
            Timber.w("ViewModel not initialized ")
        }
    }

}