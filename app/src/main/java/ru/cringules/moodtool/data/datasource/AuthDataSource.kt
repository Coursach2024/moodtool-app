package ru.cringules.moodtool.data.datasource

import retrofit2.Response
import ru.cringules.moodtool.data.api.AuthApi
import ru.cringules.moodtool.data.dto.AuthToken
import ru.cringules.moodtool.data.model.UserCredentials
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun register(userCredentials: UserCredentials): Response<AuthToken> {
        return authApi.register(userCredentials)
    }

    suspend fun login(userCredentials: UserCredentials): Response<AuthToken> {
        return authApi.login(userCredentials)
    }
}