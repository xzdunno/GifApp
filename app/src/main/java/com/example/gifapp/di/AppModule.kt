package com.example.gifapp.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.gifapp.data.db.AppDataBase
import com.example.gifapp.data.db.GifDBDao
import com.example.gifapp.data.network.RetroInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private val BASE_URL = "https://api.giphy.com/v1/gifs/"

    @Provides
    @Singleton
    fun getRetroInterface(retrofit: Retrofit): RetroInterface {
        return retrofit.create(RetroInterface::class.java)
    }

    @Provides
    @Singleton
    fun getNetworkInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getDB(context: Application): AppDataBase {
        return AppDataBase.getDataBase(context)
    }

    @Provides
    @Singleton
    fun getAppDBDao(database: AppDataBase): GifDBDao {
        return database.getGifDBDao()
    }
    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

}