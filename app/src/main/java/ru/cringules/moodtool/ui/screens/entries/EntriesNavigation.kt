package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.entriesScreen(
    onNewEntry: () -> Unit,
    onEditEntry: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    composable("entries") {
        EntriesScreen(
            onNewEntry = onNewEntry,
            onEditEntry = onEditEntry,
            modifier = modifier
        )
    }
}

fun NavController.navigateToEntriesScreen() {
    navigate("entries")
}