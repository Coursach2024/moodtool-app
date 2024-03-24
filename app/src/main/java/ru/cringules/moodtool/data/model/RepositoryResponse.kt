package ru.cringules.moodtool.data.model

import android.util.Log
import kotlinx.serialization.json.Json
import retrofit2.Response
import ru.cringules.moodtool.data.dto.ApiError
import ru.cringules.moodtool.data.dto.ApiErrorCode
import ru.cringules.moodtool.data.dto.ApiErrorResponse

sealed interface RepositoryResponse<out T> {
    data class Success<out T>(val data: T) : RepositoryResponse<T>
    data class AuthError(val error: ApiError? = null) : RepositoryResponse<Nothing>
    data class EntityError(val error: ApiError) : RepositoryResponse<Nothing>
    data class ClientError(val error: ApiError) : RepositoryResponse<Nothing>
    object FatalError : RepositoryResponse<Nothing>
    object Loading : RepositoryResponse<Nothing>

    companion object {
        suspend fun <T> fromCall(call: suspend () -> Response<T>): RepositoryResponse<T> {
            try {
                val response = call()
                if (response.isSuccessful) {
                    response.body()?.let { return Success(it) }
                }

                if (response.code() == 401 || response.code() == 403) {
                    return AuthError()
                }

                response.errorBody()?.let {
                    try {
                        val error = Json.decodeFromString<ApiErrorResponse>(it.string()).error
                        return when (error.code) {
                            ApiErrorCode.ACCESS_DENIED.code, ApiErrorCode.INCORRECT_CREDENTIALS.code -> AuthError(error)
                            ApiErrorCode.ENTITY_ALREADY_EXISTS.code, ApiErrorCode.ENTITY_DOES_NOT_EXIST.code -> EntityError(error)
                            else -> ClientError(error)
                        }
                    } catch (e: Exception) {
                        Log.e(null, null, e)
                        return FatalError
                    }
                }

                return FatalError
            } catch (e: Exception) {
                Log.e(null, null, e)
                return FatalError
            }
        }
    }
}