package com.sevenpeakssoftware.cars.data

import android.icu.text.CaseMap
import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity
@TypeConverters(ContentTypeConverter::class, TagTypeConverter::class)
data class Article(
    @PrimaryKey var id: Int,
    val title: String,
    val ingress: String,
    @ColumnInfo(name = "date_time")
    val dateTime: String,
    val tags: List<String>,
    val image: String,
    val content: List<Content>,
    val created: Int,
    val changed: Int
)