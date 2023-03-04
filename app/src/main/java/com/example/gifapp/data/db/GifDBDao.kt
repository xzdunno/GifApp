package com.example.gifapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gifapp.data.model.DBModelGif

@Dao
interface GifDBDao {
    @Query("select * from DBGif order by id asc")
    fun getAllData(): LiveData<List<DBModelGif>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(DBModelGif: DBModelGif)

    @Query("select * from DBGif where id=:id")
    fun getRecord(id: String): LiveData<DBModelGif?>

    @Query("delete from DBGif")
    suspend fun deleteAll()

    @Query("delete from DBGif where id=:id")
    fun deleteRecord(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAll(list: List<DBModelGif>)
}