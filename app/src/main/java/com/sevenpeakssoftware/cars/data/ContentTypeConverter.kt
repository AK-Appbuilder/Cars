package com.sevenpeakssoftware.cars.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class ContentTypeConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToContent(json: String?): List<Content>? {

        return json?.let{
            val type = object : TypeToken<List<Content>>() {}.type
            gson.fromJson(json, type)
        }
    }

    @TypeConverter
    fun contentToString(content: List<Content>?): String? {

       return content?.let {
           val type = object : TypeToken<List<Content>>() {}.type
           gson.toJson(content, type)
       }
    }
}