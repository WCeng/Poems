package com.wceng.poems.logic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wceng.poems.logic.dao.PoemDao
import com.wceng.poems.logic.dao.PoetDao
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.logic.model.Poet
import com.wceng.poems.util.PoemDataLoader
import kotlin.concurrent.thread


@Database(entities = [Poem::class, Poet::class], version = 2, exportSchema = false)
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
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val poemDao = getInstance(context).getPoemDao()
                        val poetDao = getInstance(context).getPoetDao()
                        thread {
                            getInstance(context).runInTransaction {
                                poemDao.insertPoems(PoemDataLoader.loadPoems())
                                poetDao.insertPoet(PoemDataLoader.loadPoets())
                            }
                        }
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build().apply {
                    instance = this
                }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE 'Poem' ADD COLUMN 'collected' INTEGER NOT NULL DEFAULT 0")
            }
        }
    }

}