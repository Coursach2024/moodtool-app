package ru.cringules.moodtool.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.main.MainScreen
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen

@Composable
fun AuthWrapper(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    when (val state = authViewModel.authState) {
        RepositoryResponse.Loading -> LoadingScreen(modifier = modifier)
        is RepositoryResponse.AuthError -> {
            NavHost(navController = navHostController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
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
                        onRegisterClick = {
                            navHostController.navigate("register")
                        },
                        modifier = modifier
                    )
                }
                composable("register") {
                    RegisterScreen(
                        login = authViewModel.userCredentials.username,
                        password = authViewModel.userCredentials.password,
                        passwordConfirmation = authViewModel.passwordConfirmation,
                        onLoginChange = { authViewModel.updateUsername(it) },
                        onPasswordChange = { authViewModel.updatePassword(it) },
                        onPasswordConfirmationChange = { authViewModel.passwordConfirmation = it },
                        onRegister = { authViewModel.register() },
                        modifier = modifier,
                        errors = state.error?.details ?: emptyList(),
                    )
                }
            }
        }

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