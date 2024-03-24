package ru.cringules.moodtool.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCredentials
import ru.cringules.moodtool.data.repository.AuthRepository
import ru.cringules.moodtool.state.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var authState: RepositoryResponse<Any> by mutableStateOf(RepositoryResponse.Loading)
        private set
    var userCredentials by mutableStateOf(UserCredentials())
        private set

    init {
        checkAuthorization()
    }

    fun checkAuthorization() {
        viewModelScope.launch {
            authState = RepositoryResponse.Loading
            authState = authRepository.checkLogin()
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
            authState = RepositoryResponse.Loading
            authState = authRepository.login(userCredentials)
        }
    }

    fun register() {
        viewModelScope.launch {
            authState = RepositoryResponse.Loading
            authState = authRepository.register(userCredentials)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            checkAuthorization()
        }
    }
}