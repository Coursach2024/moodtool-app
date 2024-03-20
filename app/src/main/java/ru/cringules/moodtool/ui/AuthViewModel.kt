package ru.cringules.moodtool.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cringules.moodtool.auth.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) {
    var authorizationState by mutableStateOf(AuthorizationState.Authorized)
        private set
}