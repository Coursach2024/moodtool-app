package ru.cringules.moodtool.auth

import retrofit2.http.Body
import retrofit2.http.POST
import ru.cringules.moodtool.model.UserCredentials

interface AuthApiService {
    @POST("v1/auth/register")
    suspend fun register(@Body userCredentials: UserCredentials): AuthToken

    @POST("v1/auth/login")
    suspend fun login(@Body userCredentials: UserCredentials): AuthToken
}