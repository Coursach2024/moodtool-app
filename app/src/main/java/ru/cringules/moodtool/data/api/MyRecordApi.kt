package ru.cringules.moodtool.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.cringules.moodtool.data.model.Info
import ru.cringules.moodtool.data.model.MoodEntry

interface MyRecordApi {
    @POST("v1/diary/my/record")
    suspend fun createMoodEntry(
        @Body moodEntry: MoodEntry,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("v1/diary/my/records")
    suspend fun getRecords(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<MoodEntry>>

    @GET("v1/diary/my/record/{id}")
    suspend fun getRecord(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<MoodEntry>

    @PATCH("v1/diary/my/record/{id}")
    suspend fun updateRecord(
        @Path("id") id: Int,
        @Body moodEntry: MoodEntry,
        @Header("Authorization") token: String
    ): Response<Unit>

    @DELETE("v1/diary/my/record/{id}")
    suspend fun deleteRecord(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("v1/diary/my/info")
    suspend fun getInfo(
        @Header("Authorization") token: String
    ): Response<Info>
}