package com.wceng.poems.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.logic.Repository

class PoemDetailViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PoemDetailViewModel(Repository) as T
    }
}