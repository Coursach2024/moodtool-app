package ru.cringules.moodtool.ui.screens.people

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen

@Composable
fun PeopleScreen(
    onPersonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    when (val state = viewModel.peopleState) {
        is RepositoryResponse.Success -> {
            PeopleList(
                people = state.data,
                onPersonClick = onPersonClick,
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
                    viewModel.loadPeople()
                }
            )
        }
    }
}

@Composable
fun PeopleList(
    people: List<String>,
    onPersonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(people) {
            Card(
                onClick = { onPersonClick(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    text = it,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun PeopleScreenPreview() {
    PeopleScreen(onPersonClick = {}, modifier = Modifier.fillMaxSize())
}