package ru.cringules.moodtool

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.cringules.moodtool.model.Mood
import ru.cringules.moodtool.model.MoodConditions
import ru.cringules.moodtool.model.MoodEntry

interface MoodApiService {
    @GET("v1/diary/my/records")
    suspend fun getMoodEntries(@Header("Authorization") token: String) : List<MoodEntry>

    @POST("v1/diary/my/record")
    suspend fun createMoodEntry(@Body moodEntry: MoodEntry, @Header("Authorization") token: String)

    @POST("api/mood/predict")
    suspend fun predictMood(@Body moodConditions: MoodConditions, @Header("Authorization") token: String): Mood
}