package ru.cringules.moodtool.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodConditions
import ru.cringules.moodtool.data.model.UserCorrelations

interface AnalyticsApi {
    @POST("v1/analytics/predict")
    suspend fun predictMood(
        @Body moodConditions: MoodConditions,
        @Header("Authorization") token: String
    ): Response<Mood>

    @GET("v1/analytics/dependencies")
    suspend fun getDependencies(
        @Header("Authorization") token: String
    ): Response<Map<String, Correlations>>

    @GET("v1/analytics/correlation")
    suspend fun getCorrelation(
        @Query("others_username") username: String,
        @Header("Authorization") token: String
    ): Response<UserCorrelations>
}