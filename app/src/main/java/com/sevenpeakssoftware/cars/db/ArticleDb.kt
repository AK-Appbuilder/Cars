package com.sevenpeakssoftware.cars.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevenpeakssoftware.cars.data.Article


@Database(
    entities = [
        Article::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDb : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}