package ru.cringules.moodtool.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PermissionUserRequest(
    val username: String
)
