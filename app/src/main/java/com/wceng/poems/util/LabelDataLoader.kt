package com.wceng.poems.util

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object LabelDataLoader {

    fun loadStr(context: Context): String {
        val input = context.assets.open("labels.txt")
        val reader = BufferedReader(InputStreamReader(input))
        return reader.readText()
    }
}