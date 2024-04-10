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
import ru.cringules.moodtool.domain.CheckStatusUseCase
import ru.cringules.moodtool.domain.LoginUseCase
import ru.cringules.moodtool.domain.LogoutUseCase
import ru.cringules.moodtool.domain.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val checkStatusUseCase: CheckStatusUseCase
) : ViewModel() {
    var authState: RepositoryResponse<Any> by mutableStateOf(RepositoryResponse.Loading)
        private set
    var userCredentials by mutableStateOf(UserCredentials())
        private set
    var passwordConfirmation: String by mutableStateOf("")

    init {
        checkAuthorization()
    }

    fun checkAuthorization() {
        viewModelScope.launch {
            authState = RepositoryResponse.Loading
            authState = checkStatusUseCase()
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
            authState = loginUseCase(userCredentials)
        }
    }

    fun register() {
        viewModelScope.launch {
            authState = RepositoryResponse.Loading
            authState = registerUseCase(userCredentials)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            checkAuthorization()
        }
    }
}