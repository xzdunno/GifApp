package com.example.gifapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gifapp.data.model.DBModelGif
//База данных для сохранения страницы Trending
@Database(
    entities = [DBModelGif::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getGifDBDao(): GifDBDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "DBGif"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

        }
    }
}