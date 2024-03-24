package ru.cringules.moodtool.data.repository

import ru.cringules.moodtool.data.datasource.AuthDataSource
import ru.cringules.moodtool.data.datasource.MyRecordDataSource
import ru.cringules.moodtool.data.datasource.TokenDataSource
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCredentials
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenDataSource: TokenDataSource,
    private val myRecordDataSource: MyRecordDataSource
) {
    suspend fun checkLogin(): RepositoryResponse<Any> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.getInfo(token)
        }
    }

    suspend fun register(userCredentials: UserCredentials): RepositoryResponse<Any> {
        val response = RepositoryResponse.fromCall { authDataSource.register(userCredentials) }
        val token = if (response is RepositoryResponse.Success) response.data.jwt else null
        tokenDataSource.setToken(token)
        return response
    }

    suspend fun login(userCredentials: UserCredentials): RepositoryResponse<Any> {
        val response = RepositoryResponse.fromCall { authDataSource.login(userCredentials) }
        val token = if (response is RepositoryResponse.Success) response.data.jwt else null
        tokenDataSource.setToken(token)
        return response
    }

    suspend fun logout() {
        tokenDataSource.setToken(null)
    }
}