package com.sevenpeakssoftware.cars.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        fun create(app: Application): ArticleDb {

            return Room
                .databaseBuilder(app, ArticleDb::class.java, "article.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}