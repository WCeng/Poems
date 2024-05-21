package com.wceng.poems.logic.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wceng.poems.logic.model.Poem

@Dao
interface PoemDao {

    @Insert
    fun insertPoem(poem: Poem)

    @Insert
    fun insertPoems(poems: List<Poem>)

    @Delete
    fun deletePoem(poem: Poem)

    @Query("DELETE FROM Poem")
    fun deleteAllPoems()

    @Update
    fun updatePoem(poem: Poem)

    @Query("SELECT * FROM Poem")
    fun queryAllPoem(): PagingSource<Int, Poem>

    @Query("SELECT * FROM Poem WHERE poet = :poet")
    fun queryPoemByPoet(poet: String): PagingSource<Int, Poem>

    @Query("SELECT * FROM Poem WHERE labels like '%' || :label || '%'")
    fun queryPoemByLabel(label: String): PagingSource<Int, Poem>

    @Query("SELECT * FROM Poem WHERE id = :id")
    fun queryPoemById(id: Long): LiveData<Poem>

    @Query("SELECT * FROM Poem WHERE title = :title")
    fun queryPoemByTitle(title: String): PagingSource<Int, Poem>

    @Query("SELECT * FROM Poem WHERE collected = 1")
    fun queryCollectedPoems(): PagingSource<Int, Poem>

    @Query(
        "SELECT * FROM Poem WHERE title  LIKE '%' || :s || '%' " +
                "OR poet LIKE '%' || :s || '%'" +
                "OR dynasty LIKE '%' || :s || '%'" +
                "OR content LIKE '%' || :s || '%'" +
                ""
    )
    fun queryPoemsLike(s: String): PagingSource<Int, Poem>
}