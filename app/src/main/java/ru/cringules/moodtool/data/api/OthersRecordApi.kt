package ru.cringules.moodtool.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.cringules.moodtool.data.model.MoodEntry

interface OthersRecordApi {
    @GET("v1/diary/others/records")
    suspend fun getRecords(
        @Query("others_username") username: String,
        @Header("Authorization") token: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<MoodEntry>>
}