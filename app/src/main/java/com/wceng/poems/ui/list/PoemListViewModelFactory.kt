package com.wceng.poems.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wceng.poems.logic.Repository

class PoemListViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PoemListViewModel(Repository) as T
    }
}