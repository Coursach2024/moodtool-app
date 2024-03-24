package ru.cringules.moodtool.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,
    val message: String,
    val details: List<String>
)
