package com.wceng.poems.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wceng.poems.logic.model.Poet

@Dao
interface PoetDao {

    @Insert
    suspend fun insertPoet(poet: Poet)

    @Insert
    fun insertPoet(poets: List<Poet>)

    @Delete
    suspend fun deletePoet(poet: Poet)

    @Query("delete from Poet")
    suspend fun deleteAllPoets()

    @Update
    suspend fun update(poet: Poet)

    @Query("select * from Poet where name = :name")
    fun queryByName(name: String): LiveData<Poet>

    @Query("select * from Poet where name like '%' || :s || '%'")
    fun queryLike(s: String): LiveData<List<Poet>>

    @Query("select * from Poet")
    suspend fun queryAllPoets(): List<Poet>
}