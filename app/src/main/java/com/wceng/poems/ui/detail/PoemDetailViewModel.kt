package com.wceng.poems.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.wceng.poems.logic.Repository
import com.wceng.poems.logic.model.Poem
import kotlinx.coroutines.launch

class PoemDetailViewModel(private val repository: Repository) : ViewModel() {

    private val poemIdLiveData = MutableLiveData<Long>()
    private val poetNameLiveData = MutableLiveData<String>()

    val poemLiveData = poemIdLiveData.switchMap { poemId ->
        repository.queryPoemById(poemId)
    }

    val poetLiveData = poetNameLiveData.switchMap { poetName ->
        repository.queryPoet(poetName)
    }

    fun queryPoem(id: Long) {
        poemIdLiveData.value = id
    }

    fun queryPoet(name: String) {
        poetNameLiveData.value = name
    }

    fun updatePoem(poem: Poem) {
        viewModelScope.launch {
            repository.updatePoem(poem)
        }
    }

    fun getPoemSpeakText(): String {
        val sb = StringBuilder()
        poemLiveData.value?.let { poem ->
            sb.append(poem.title)
            sb.append("。${poem.dynasty}")
            sb.append("。${poem.poet}")
            sb.append("。${poem.content}")
        }
        return sb.toString()
    }

}