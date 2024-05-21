package com.wceng.poems.logic.dao

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wceng.poems.PoemApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LabelDao {

    private const val LABEL_KEY = "label"
    private val gson = Gson()

    private val sp by lazy {
        PoemApp.context.getSharedPreferences(LABEL_KEY, Context.MODE_PRIVATE)
    }

    private fun getLabels(): List<String> {
        val string = sp.getString(LABEL_KEY, "")
        val typeToken = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(string, typeToken)
    }

    fun insertLabels(labels: String) {
        sp.edit().putString(LABEL_KEY, labels).apply()
    }

    suspend fun queryLabelsLike(query: String): List<String> {
        return withContext(Dispatchers.IO) {
            getLabels().filter { s ->
                return@filter s.contains(query)
            }
        }
    }

}