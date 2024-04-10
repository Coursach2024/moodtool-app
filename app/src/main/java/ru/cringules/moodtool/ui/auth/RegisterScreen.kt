package ru.cringules.moodtool.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cringules.moodtool.ui.elements.PasswordField

@Composable
fun RegisterScreen(
    login: String,
    password: String,
    passwordConfirmation: String,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
    errors: List<String> = emptyList(),
) {
    Surface(modifier = modifier) {
        Column {
            for (error in errors) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
            TextField(
                value = login,
                onValueChange = onLoginChange,
                label = {
                    Text(text = "Login")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true
            )
            PasswordField(
                value = password,
                onValueChange = onPasswordChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            PasswordField(
                value = passwordConfirmation,
                onValueChange = onPasswordConfirmationChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = "Confirm password"
            )
            Button(
                onClick = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = password.isNotEmpty() && password == passwordConfirmation
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(
        login = "",
        password = "",
        passwordConfirmation = "",
        onLoginChange = {},
        onPasswordChange = {},
        onPasswordConfirmationChange = {},
        onRegister = {}
    )
}