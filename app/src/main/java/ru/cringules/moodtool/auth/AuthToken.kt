package ru.cringules.moodtool.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val token: String
)
