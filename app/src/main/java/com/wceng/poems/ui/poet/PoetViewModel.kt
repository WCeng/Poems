package com.wceng.poems.ui.poet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.wceng.poems.logic.Repository

class PoetViewModel(private val repository: Repository) : ViewModel() {

    private val poetQueryLiceData = MutableLiveData<String>()

    val poetLiveData = poetQueryLiceData.switchMap { query ->
        repository.queryPoetLike(query)
    }

    fun queryPoetLike(name: String) {
        poetQueryLiceData.value = name
    }

}