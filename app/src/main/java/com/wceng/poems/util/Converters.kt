package com.wceng.poems.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromLabelList(labels: List<String>): String {
        return Gson().toJson(labels)
//        return labels.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toLabelList(str: String): List<String> {
        val typeToken = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(str, typeToken)
    }
}