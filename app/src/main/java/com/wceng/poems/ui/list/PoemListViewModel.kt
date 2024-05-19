package com.wceng.poems.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wceng.poems.logic.Repository
import com.wceng.poems.logic.model.Poem
import kotlinx.coroutines.flow.Flow

class PoemListViewModel(private val repository: Repository) : ViewModel() {

    fun queryPoems(query: String, actionType: String): Flow<PagingData<Poem>> {
        when (actionType) {
            PoemListActivity.ACTION_LIKE -> {
                return repository.queryPoemsLike(query).cachedIn(viewModelScope)
            }

            PoemListActivity.ACTION_POET -> {
                return repository.queryPoemByPoet(query).cachedIn(viewModelScope)
            }

            PoemListActivity.ACTION_TITLE -> {
                return repository.queryPoemByTitle(query).cachedIn(viewModelScope)
            }

            else -> {
                throw IllegalArgumentException("actionType is unKnow")
            }
        }
    }

}