package com.example.gifapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gifapp.data.model.Gifs
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class SearchPagingSource @AssistedInject constructor(
    private val retroInterface: RetroInterface,
    @Assisted("q") private val q: String
) : PagingSource<Int, Gifs>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gifs> {
        if (q.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(limit)
            val response = retroInterface.search(q, pageNumber, pageSize)

            if (response.isSuccessful) {
                val gifs = response.body()!!.data
                val nextPageNumber = if (gifs.isEmpty()) null else pageNumber + limit
                val prevPageNumber = if (pageNumber > limit) pageNumber - limit else null
                return LoadResult.Page(gifs, prevPageNumber, nextPageNumber)
            } else {

                return LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gifs>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(limit) ?: anchorPage.nextKey?.minus(limit)
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("q") q: String): SearchPagingSource
    }

    companion object {
        const val limit = 25
        const val INITIAL_PAGE_NUMBER = 0
    }

}