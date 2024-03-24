package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.newEntryScreen(onSaved: () -> Unit, modifier: Modifier = Modifier) {
    composable("entries/new") {
        EditEntryScreen(onSaved = onSaved, modifier = modifier)
    }
}

fun NavController.navigateToNewEntryScreen() {
    navigate("entries/new")
}

fun NavGraphBuilder.editEntryScreen(onSaved: () -> Unit, modifier: Modifier = Modifier) {
    composable(
        "entries/{entryId}",
        arguments = listOf(navArgument("entryId") { type = NavType.IntType })
    ) {
        EditEntryScreen(onSaved = onSaved, modifier = modifier)
    }
}

fun NavController.navigateToEditEntryScreen(entryId: Int) {
    navigate("entries/$entryId")
}