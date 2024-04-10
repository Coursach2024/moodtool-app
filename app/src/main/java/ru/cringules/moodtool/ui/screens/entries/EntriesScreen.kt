package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.elements.MoodEntryList
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen

@Composable
fun EntriesScreen(
    onNewEntry: () -> Unit,
    onEditEntry: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntriesViewModel = hiltViewModel(),
) {
    when (val entriesState = viewModel.entriesState) {
        is RepositoryResponse.Success -> {
            Scaffold(
                modifier = modifier,
                floatingActionButton = {
                    FloatingActionButton(onClick = onNewEntry) {
                        // todo fix
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                    }
                }
            ) {
                MoodEntryList(
                    entries = entriesState.data,
                    onEditEntry = onEditEntry,
                    modifier = Modifier.padding(it)
                )
            }
        }

        RepositoryResponse.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        else -> {
            ErrorScreen(
                modifier = modifier,
                canRetry = true,
                onRetry = {
                    viewModel.loadMoodEntries()
                })
        }
    }
}

@Preview
@Composable
private fun EntriesScreenPreview() {
    EntriesScreen(
        onNewEntry = {},
        onEditEntry = {},
        modifier = Modifier.fillMaxSize()
    )
}