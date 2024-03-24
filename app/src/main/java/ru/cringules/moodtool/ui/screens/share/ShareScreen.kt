package ru.cringules.moodtool.ui.screens.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen

@Composable
fun ShareScreen(
    modifier: Modifier = Modifier,
    viewModel: ShareViewModel = hiltViewModel()
) {
    when (val state = viewModel.usersState) {
        is RepositoryResponse.Success -> {
            ShareContent(
                users = state.data,
                onRemoveUser = { viewModel.removeUser(it) },
                onAddUser = { viewModel.addUser(it) },
                modifier = modifier
            )
        }

        RepositoryResponse.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        else -> {
            ErrorScreen(
                modifier = modifier,
                canRetry = true,
                onRetry = {
                    viewModel.loadUsers()
                }
            )
        }
    }
}

@Composable
fun ShareContent(
    users: List<String>,
    onRemoveUser: (String) -> Unit,
    onAddUser: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val enteredUsername = remember {
        mutableStateOf("")
    }

    Column(modifier = modifier) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(users) {
                UserCard(
                    username = it,
                    onRemove = { onRemoveUser(it) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = enteredUsername.value,
                onValueChange = { enteredUsername.value = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                label = {
                    Text(text = "Username")
                }
            )
            IconButton(onClick = {
                onAddUser(enteredUsername.value)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun UserCard(
    username: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = username,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = onRemove) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Remove")
            }
        }
    }
}

@Preview
@Composable
private fun ShareScreenPreview() {
    ShareScreen()
}