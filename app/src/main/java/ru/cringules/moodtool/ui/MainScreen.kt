package ru.cringules.moodtool.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.cringules.moodtool.MoodEntriesState
import ru.cringules.moodtool.MoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MoodViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable(route = "main") {
            when (val loadingState = viewModel.loadingState) {
                is MoodEntriesState.Success -> {
                    EntriesScreen(
                        moodEntries = loadingState.moodEntries,
                        onNewEntry = {
                            viewModel.resetNewMoodEntry()
                            navController.navigate(route = "new")
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is MoodEntriesState.Loading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }

                is MoodEntriesState.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }

        composable(route = "new") {
            NewEntryScreen(
                moodEntry = viewModel.state.newMoodEntry,
                prediction = viewModel.predictedMoodState,
                onMoodEntryChange = {
                    viewModel.updateNewMoodEntry(it)
                },
                onAdd = {
                    viewModel.createMoodEntry()
                    navController.popBackStack(route = "main", inclusive = false)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}