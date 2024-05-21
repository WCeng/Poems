package com.wceng.poems.logic

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wceng.poems.PoemApp
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.logic.model.Poet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

object Repository {

    private val poemDao
        get() = PoemDB.getInstance(PoemApp.context).getPoemDao()

    private val poetDao
        get() = PoemDB.getInstance(PoemApp.context).getPoetDao()

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

    fun queryPoemByLabel(s: String): Flow<PagingData<Poem>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { poemDao.queryPoemByLabel(s) })
            .flow
    }

    fun queryCollectedPoems(): Flow<PagingData<Poem>> {
        return Pager(
            config = PagingConfig(pageSize = 50),
            pagingSourceFactory = { poemDao.queryCollectedPoems() })
            .flow
    }

    fun queryPoemById(id: Long): LiveData<Poem> {
        return poemDao.queryPoemById(id)
    }

    fun queryPoet(name: String): LiveData<Poet> {
        return poetDao.queryByName(name)
    }


    fun queryPoetLike(s: String): LiveData<List<Poet>> {
        return poetDao.queryLike(s)
    }

    suspend fun updatePoem(poem: Poem){
        withContext(Dispatchers.IO){
            poemDao.updatePoem(poem)
        }
    }

}