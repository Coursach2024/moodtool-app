package ru.cringules.moodtool.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.auth.AuthState

@Composable
fun AuthWrapper(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    when (authViewModel.authState) {
        AuthState.Loading -> LoadingScreen(modifier = modifier)
        AuthState.Unauthorized -> LoginScreen(
            login = authViewModel.userCredentials.username,
            password = authViewModel.userCredentials.password,
            onLoginChange = {
                authViewModel.updateUsername(it)
            },
            onPasswordChange = {
                authViewModel.updatePassword(it)
            },
            onLogin = {
                authViewModel.login()
            },
            onRegister = {
                authViewModel.register()
            },
            modifier = modifier
        )
        AuthState.Authorized -> MainScreen(modifier = modifier)
        AuthState.Error -> ErrorScreen(modifier = modifier)
    }
}