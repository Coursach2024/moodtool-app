package ru.cringules.moodtool.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.cringules.moodtool.data.dto.AuthToken
import ru.cringules.moodtool.data.model.UserCredentials

interface AuthApi {
    @POST("v1/auth/register")
    suspend fun register(@Body userCredentials: UserCredentials): Response<AuthToken>

    @POST("v1/auth/login")
    suspend fun login(@Body userCredentials: UserCredentials): Response<AuthToken>
}