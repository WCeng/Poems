package com.wceng.poems

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class PoemApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}