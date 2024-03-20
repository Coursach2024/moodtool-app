package ru.cringules.moodtool.auth

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/register")
    suspend fun register(@Body userCredentials: UserCredentials) : AuthToken

    @POST("api/auth/login")
    suspend fun login(@Body userCredentials: UserCredentials) : AuthToken
}