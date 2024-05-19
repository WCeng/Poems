package com.wceng.poems.logic.dao

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wceng.poems.PoemApp
import com.wceng.poems.logic.model.Poem

object PoemsCollectedDao {

    private const val POEM_COLLECTED_KEY = "poemCollected"
    private val gson = Gson()

    private val sp by lazy {
        PoemApp.context.getSharedPreferences(POEM_COLLECTED_KEY, Context.MODE_PRIVATE)
    }

    fun save(poem: Poem) {
        val allPoems = getAll().toMutableList()
        allPoems.add(poem)
        val json = gson.toJson(allPoems)
        sp.edit().putString(POEM_COLLECTED_KEY, json).apply()
    }

    fun get(poemId: Long): Poem? {
        return getAll().find { it.id == poemId }
    }

    fun getAll(): List<Poem> {
        val json = sp.getString(POEM_COLLECTED_KEY, "[]")
        val type = object : TypeToken<List<Poem>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}
