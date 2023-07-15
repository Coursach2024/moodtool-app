package ru.cringules.moodtool

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MoodApiService {
    @GET("entries")
    suspend fun getMoodEntries() : List<MoodEntry>

    @POST("entries")
    suspend fun createMoodEntry(@Body moodEntry: MoodEntry)
}