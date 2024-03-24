package ru.cringules.moodtool.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.cringules.moodtool.data.model.PermissionUserRequest

interface PermissionApi {
    @POST("/v1/permission/issue")
    suspend fun issuePermission(
        @Body request: PermissionUserRequest,
        @Header("Authorization") token: String,
    ): Response<Unit>

    @POST("/v1/permission/recall")
    suspend fun recallPermission(
        @Body request: PermissionUserRequest,
        @Header("Authorization") token: String,
    ): Response<Unit>

    @GET("/v1/permission/issuers_list")
    suspend fun getIssuers(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<String>>

    @GET("/v1/permission/recipients_list")
    suspend fun getRecipients(
        @Header("Authorization") token: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 100
    ): Response<List<String>>
}