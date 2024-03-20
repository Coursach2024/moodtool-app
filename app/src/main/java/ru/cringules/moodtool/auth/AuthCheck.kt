package ru.cringules.moodtool.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthCheck(
    val username: String
)
