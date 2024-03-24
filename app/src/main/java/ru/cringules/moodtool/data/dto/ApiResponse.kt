package ru.cringules.moodtool.data.dto

import kotlinx.serialization.json.Json
import retrofit2.Response

sealed interface ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>
    data class BusinessError(val code: Int, val error: ApiError) : ApiResponse<Nothing>
    data class NetworkError(val error: Exception) : ApiResponse<Nothing>

    companion object {
        suspend fun <T> fromCall(call: suspend () -> Response<T>): ApiResponse<T> {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()!!
                    return Success(body)
                }

                response.errorBody()?.let {
                    val error = Json.decodeFromString<ApiErrorResponse>(it.string())
                    return BusinessError(response.code(), error.error)
                }

                return NetworkError(RuntimeException())
            } catch (e: Exception) {
                return NetworkError(e)
            }
        }
    }
}