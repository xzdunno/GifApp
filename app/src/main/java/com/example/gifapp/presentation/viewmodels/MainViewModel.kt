package com.example.gifapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifapp.data.model.DBModelGif
import com.example.gifapp.data.model.Data
import com.example.gifapp.data.network.ListenNetwork
import com.example.gifapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val listenNetwork: ListenNetwork
) : ViewModel() {
    var dataResp: MutableLiveData<Data?> = MutableLiveData()
    val isConnected: Flow<Boolean> = listenNetwork.isConnected
    fun trending() {
        viewModelScope.launch {
            dataResp.value = repository.trending()
        }
    }

    fun getAllData(): LiveData<List<DBModelGif>> {
        return repository.getAllData()
    }

    fun deleteAll() {
        viewModelScope.launch { repository.deleteAll() }
    }

    fun insertAll(data: Data) {
        viewModelScope.launch { repository.insertAll(data) }
    }
}