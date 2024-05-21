package com.wceng.poems.ui.label

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.logic.Repository
import com.wceng.poems.logic.dao.LabelDao

class LabelViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LabelViewModel(Repository, LabelDao) as T
    }
}