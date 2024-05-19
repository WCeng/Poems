package com.wceng.poems.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wceng.poems.PoemApp
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.logic.model.Poet
import java.io.BufferedReader
import java.io.InputStreamReader

object PoemDataLoader {

    fun loadPoems(): List<Poem> {
        val inputStream = PoemApp.context.assets.open("poems.txt")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val text = bufferedReader.readText()
        val typeToken = object : TypeToken<List<Poem>>() {}.type
        return Gson().fromJson(text, typeToken)
    }

    fun loadPoets(): List<Poet> {
        val inputStream = PoemApp.context.assets.open("poets.txt")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val text = bufferedReader.readText()
        val typeToken = object : TypeToken<List<Poet>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
}