package ru.cringules.moodtool.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val error: ApiError
)
