package ru.cringules.moodtool.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.cringules.moodtool.MoodApiService
import ru.cringules.moodtool.MoodEntry

interface MoodEntriesRepository {
    suspend fun getMoodEntries() : List<MoodEntry>
    suspend fun createMoodEntry(moodEntry: MoodEntry)
}

class MoodEntriesApiRepository : MoodEntriesRepository {
    private val baseUrl = "http://192.168.0.106:8080"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService by lazy {
        retrofit.create(MoodApiService::class.java)
    }

    override suspend fun getMoodEntries(): List<MoodEntry> {
        return retrofitService.getMoodEntries()
    }

    override suspend fun createMoodEntry(moodEntry: MoodEntry) {
        retrofitService.createMoodEntry(moodEntry)
    }
}
