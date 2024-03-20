package ru.cringules.moodtool.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.model.UserCredentials
import ru.cringules.moodtool.auth.AuthRepository
import ru.cringules.moodtool.auth.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var authState by mutableStateOf(AuthState.Loading)
        private set
    var userCredentials by mutableStateOf(UserCredentials())
        private set

    init {
        checkAuthorization()
    }

    private fun checkAuthorization() {
        viewModelScope.launch {
            authState = AuthState.Loading
            authState = authRepository.checkAuth()
        }
    }

    fun updateUsername(username: String) {
        userCredentials = userCredentials.copy(username = username)
    }

    fun updatePassword(password: String) {
        userCredentials = userCredentials.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            authState = AuthState.Loading
            authState = authRepository.login(userCredentials.username, userCredentials.password)
        }
    }

    fun register() {
        viewModelScope.launch {
            authState = AuthState.Loading
            authState = authRepository.register(userCredentials.username, userCredentials.password)
        }
    }
}