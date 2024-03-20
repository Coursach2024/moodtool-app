package ru.cringules.moodtool.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.cringules.moodtool.MoodApiService
import ru.cringules.moodtool.auth.AuthApiService

@Module
@InstallIn(SingletonComponent::class)
class MoodEntriesModule {
    private val baseUrl = "http://130.193.39.108:8080"

    @Provides
    fun moodApiService(): MoodApiService {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
            .create(MoodApiService::class.java)
    }
}