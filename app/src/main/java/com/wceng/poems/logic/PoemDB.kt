package com.wceng.poems.logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wceng.poems.logic.dao.PoemDao
import com.wceng.poems.logic.dao.PoetDao
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.logic.model.Poet


@Database(entities = [Poem::class, Poet::class], version = 1)
abstract class PoemDB : RoomDatabase() {

    abstract fun getPoemDao(): PoemDao
    abstract fun getPoetDao(): PoetDao

    companion object {
        private var instance: PoemDB? = null

        @Synchronized
        fun getInstance(context: Context): PoemDB {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context, PoemDB::class.java, "Poems.db")
                .addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
//                        println("xwceng")
//                        val poemDao = getInstance(context).getPoemDao()
//                        val poetDao = getInstance(context).getPoetDao()
//                        thread {
//                            println("start")
//                            poemDao.insertPoems(PoemDataLoader.loadPoems())
//                            poetDao.insertPoet(PoemDataLoader.loadPoets())
//                            println("success")
//                        }

                    }
                })
                .build().apply {
                    instance = this
                }
        }
    }
}