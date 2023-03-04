package com.example.gifapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.gifapp.data.db.GifDBDao
import com.example.gifapp.data.model.DBModelGif
import com.example.gifapp.data.model.Data
import com.example.gifapp.data.model.DataCard
import com.example.gifapp.data.model.Gifs
import com.example.gifapp.data.network.ApiClient
import com.example.gifapp.data.network.SearchPagingSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiClient: ApiClient,
    private val gifDBDao: GifDBDao,
    private val pageSource: SearchPagingSource.Factory
) {
    suspend fun trending(): Data? {
        val resp = apiClient.trending()
        if (resp.failed) return null
        if (!resp.isSuccessful) return null
        return resp.body

    }

    fun getAllData(): LiveData<List<DBModelGif>> {
        return gifDBDao.getAllData()
    }

    suspend fun insertAll(data: Data) {
        val list = mutableListOf<DBModelGif>()
        for (item in data.data) {
            list.add(
                DBModelGif(
                    item.id,
                    item.images.original.url,
                    item.username,
                    item.title,
                    item.rating,
                    item.images.original.width,
                    item.images.original.height
                )
            )
        }
        gifDBDao.insertAll(list)
    }

    fun getRecord(id: String): LiveData<DBModelGif?> {
        return gifDBDao.getRecord(id)
    }

    suspend fun deleteAll() {
        gifDBDao.deleteAll()
    }

    suspend fun getCard(id: String): DataCard? {
        val resp = apiClient.getCard(id)
        if (resp.failed) return null
        if (!resp.isSuccessful) return null
        return resp.body
    }

    fun getPageSource(q: String): PagingSource<Int, Gifs> {
        return pageSource.create(q)
    }
}