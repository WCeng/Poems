package com.wceng.poems.ui.poet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.logic.Repository

class PoetViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PoetViewModel(Repository) as T
    }
}