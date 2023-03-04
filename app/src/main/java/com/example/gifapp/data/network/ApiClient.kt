package com.example.gifapp.data.network

import com.example.gifapp.data.model.Data
import com.example.gifapp.data.model.DataCard
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(private val retroInterface: RetroInterface) {
    suspend fun trending(): SimpleResponse<Data> {
        return safeApiCall { retroInterface.trending() }
    }

    suspend fun getCard(id: String): SimpleResponse<DataCard> {
        return safeApiCall { retroInterface.getCard(id) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}