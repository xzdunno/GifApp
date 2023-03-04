package com.example.gifapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gifapp.data.model.Gifs
import com.example.gifapp.data.network.ListenNetwork
import com.example.gifapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
    private val listenNetwork: ListenNetwork
) : ViewModel() {
    val isConnected: Flow<Boolean> = listenNetwork.isConnected
    private val _query = MutableStateFlow("")
    private val query: Flow<String> = _query.asStateFlow()
    val gifs: Flow<PagingData<Gifs>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)

    private fun newPager(q: String): Pager<Int, Gifs> {
        return Pager(PagingConfig(25, enablePlaceholders = true)) {
            repository.getPageSource(q)
        }
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}