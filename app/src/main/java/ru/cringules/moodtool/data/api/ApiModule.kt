package ru.cringules.moodtool.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val apiUrl = "http://192.168.1.69:8080"

    @Provides
    fun provideAnalyticsApi(): AnalyticsApi {
        return buildApi()
    }

    @Provides
    fun provideAuthApi(): AuthApi {
        return buildApi()
    }

    @Provides
    fun provideMyRecordApi(): MyRecordApi {
        return buildApi()
    }

    @Provides
    fun provideOthersRecordApi(): OthersRecordApi {
        return buildApi()
    }

    @Provides
    fun providePermissionApi(): PermissionApi {
        return buildApi()
    }

    private inline fun <reified T> buildApi(): T {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(apiUrl)
            .build()
            .create(T::class.java)
    }
}