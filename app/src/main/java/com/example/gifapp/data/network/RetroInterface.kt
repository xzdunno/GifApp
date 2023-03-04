package com.example.gifapp.data.network


import com.example.gifapp.BuildConfig
import com.example.gifapp.data.model.Data
import com.example.gifapp.data.model.DataCard
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroInterface {
    @GET("search?api_key=${BuildConfig.API_KEY}")
    suspend fun search(
        @Query("q") q: String,
        @Query("offset") @androidx.annotation.IntRange(from = 0) page: Int = 0,
        @Query("limit") limit: Int = 25
    ): Response<Data>

    @GET("{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getCard(@Path("id") id: String): Response<DataCard>

    @GET("trending?api_key=${BuildConfig.API_KEY}&limit=25")
    suspend fun trending(): Response<Data>
}