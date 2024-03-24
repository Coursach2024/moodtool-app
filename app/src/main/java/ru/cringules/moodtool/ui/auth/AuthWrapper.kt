package ru.cringules.moodtool.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.main.MainScreen
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen

@Composable
fun AuthWrapper(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    when (val state = authViewModel.authState) {
        RepositoryResponse.Loading -> LoadingScreen(modifier = modifier)
        is RepositoryResponse.AuthError -> LoginScreen(
            login = authViewModel.userCredentials.username,
            password = authViewModel.userCredentials.password,
            errors = state.error?.details ?: emptyList(),
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

        is RepositoryResponse.Success -> MainScreen(
            onLogout = { authViewModel.logout() },
            modifier = modifier
        )

        else -> ErrorScreen(
            modifier = modifier,
            canRetry = true,
            onRetry = { authViewModel.checkAuthorization() }
        )
    }
}