package com.wceng.poems

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import com.wceng.poems.logic.dao.LabelDao
import com.wceng.poems.util.LabelDataLoader

class PoemApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        if (isFirstSetup()) {
            val loadStr = LabelDataLoader.loadStr(this)
            LabelDao.insertLabels(loadStr)
        }
    }

    private fun isFirstSetup(): Boolean {
        var result = false

        val sp = getSharedPreferences("app", Context.MODE_PRIVATE)
        val isFirst = sp.getBoolean("isFirst", true)
        if (isFirst) {
            result = true
            sp.edit().putBoolean("isFirst", false).apply()
        }
        return result
    }
}