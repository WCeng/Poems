package com.wceng.poems.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wceng.poems.logic.model.Poem

@Dao
interface PoemDao {

    @Insert
    suspend fun insertPoem(poem: Poem)

    @Delete
    suspend fun deletePoem(poem: Poem)

    @Query("DELETE FROM Poem")
    suspend fun deleteAllPoems()

    @Update
    suspend fun updatePoem(poem: Poem)

    @Query("SELECT * FROM Poem")
    suspend fun queryAllPoem(): List<Poem>

    @Query("SELECT * FROM Poem WHERE poet = :poet")
    suspend fun queryPoemByPoet(poet: String): List<Poem>

    @Query("SELECT * FROM Poem WHERE id = :id")
    suspend fun queryPoemById(id: Long): List<Poem>
}