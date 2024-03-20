package ru.cringules.moodtool.auth

import android.util.Log
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import ru.cringules.moodtool.MoodApiService
import ru.cringules.moodtool.model.UserCredentials
import ru.cringules.moodtool.data.AuthDataStore
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val authApiService: AuthApiService,
    private val moodApiService: MoodApiService
) {

    suspend fun checkAuth(): AuthState {
        val token = authDataStore.getToken.first() ?: return AuthState.Unauthorized

        return try {
            moodApiService.getMoodEntries("Bearer $token")
            AuthState.Authorized
        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 403) {
                AuthState.Unauthorized
            } else {
                AuthState.Error
            }
        } catch (e: Exception) {
            AuthState.Error
        }
    }

    suspend fun register(username: String, password: String): AuthState {
        return try {
            val token = authApiService.register(UserCredentials(username, password))
            authDataStore.saveToken(token.token)
            AuthState.Authorized
        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 403) {
                AuthState.Unauthorized
            } else {
                AuthState.Error
            }
        } catch (e: Exception) {
            AuthState.Error
        }
    }

    suspend fun login(username: String, password: String): AuthState {
        return try {
            val token = authApiService.login(UserCredentials(username, password))
            authDataStore.saveToken(token.token)
            AuthState.Authorized
        } catch (e: HttpException) {
            if (e.code() == 401 || e.code() == 403) {
                AuthState.Unauthorized
            } else {
                Log.e("", "", e)
                AuthState.Error
            }
        } catch (e: Exception) {
            Log.e("", "", e)
            AuthState.Error
        }
    }
}