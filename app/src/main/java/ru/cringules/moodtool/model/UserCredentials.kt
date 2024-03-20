package ru.cringules.moodtool.model

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val username: String = "",
    val password: String = ""
)
