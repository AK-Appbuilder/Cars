package com.sevenpeakssoftware.cars.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sevenpeakssoftware.cars.data.Article
import io.reactivex.Flowable


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(article: List<Article>)

    @Query("SELECT * FROM Article ")
    fun loadArticles(): Flowable<List<Article>>
}