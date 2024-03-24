package ru.cringules.moodtool.state

sealed interface AuthState {
    object Loading : AuthState
    data class Unauthorized(val errors: List<String>) : AuthState
    object Authorized : AuthState
    object Error : AuthState
}