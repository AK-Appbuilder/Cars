package com.sevenpeakssoftware.cars.api

import com.sevenpeakssoftware.cars.data.Article

data class ArticlesResponse (val status:String, val content:List<Article>)