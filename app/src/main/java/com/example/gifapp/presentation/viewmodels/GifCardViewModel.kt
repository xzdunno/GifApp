package com.example.gifapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifapp.data.model.DBModelGif
import com.example.gifapp.data.model.DataCard
import com.example.gifapp.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifCardViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val cardResp: MutableLiveData<DataCard?> = MutableLiveData()
    fun getCard(id: String) {
        viewModelScope.launch { cardResp.value = repository.getCard(id) }
    }

    fun getRecord(id: String): LiveData<DBModelGif?> {
        return repository.getRecord(id)
    }

}