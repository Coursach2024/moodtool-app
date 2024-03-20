package ru.cringules.moodtool

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.cringules.moodtool.auth.AuthApiService
import ru.cringules.moodtool.auth.AuthToken
import ru.cringules.moodtool.model.UserCredentials

class AuthService {
    private val baseUrl = "http://130.193.39.108:8080"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    suspend fun login(userCredentials: UserCredentials): AuthToken {
        return retrofitService.login(userCredentials)
    }

    suspend fun register(userCredentials: UserCredentials): AuthToken {
        return retrofitService.register(userCredentials)
    }
}