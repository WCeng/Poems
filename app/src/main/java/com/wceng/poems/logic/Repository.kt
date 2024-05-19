package com.wceng.poems.logic

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wceng.poems.PoemApp
import com.wceng.poems.logic.dao.PoemsCollectedDao
import com.wceng.poems.logic.model.Poem
import kotlinx.coroutines.flow.Flow

object Repository {

    private val poemDao
        get() = PoemDB.getInstance(PoemApp.context).getPoemDao()

    private val poetDao
        get() = PoemDB.getInstance(PoemApp.context).getPoetDao()

    private val poetCollectedDao
        get() = PoemsCollectedDao

    fun queryPoemsLike(s: String): Flow<PagingData<Poem>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { poemDao.queryPoemsLike(s) })
            .flow
    }

    fun queryPoemByTitle(s: String): Flow<PagingData<Poem>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { poemDao.queryPoemByTitle(s) })
            .flow
    }

    fun queryPoemByPoet(s: String): Flow<PagingData<Poem>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { poemDao.queryPoemByPoet(s) })
            .flow
    }

}