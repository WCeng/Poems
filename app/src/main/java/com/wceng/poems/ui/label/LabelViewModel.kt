package com.wceng.poems.ui.label

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wceng.poems.logic.Repository
import com.wceng.poems.logic.dao.LabelDao
import kotlinx.coroutines.launch

class LabelViewModel(
    private val repository: Repository,
    private val labelDao: LabelDao
) : ViewModel() {

    val labelLiveData
        get() = _labelLiveData
    private val _labelLiveData = MutableLiveData<List<String>>()

    fun queryLabelLike(query: String) {
        viewModelScope.launch {
            val queryLabels = labelDao.queryLabelsLike(query)
            _labelLiveData.value = queryLabels
        }
    }

}