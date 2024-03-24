package ru.cringules.moodtool.ui.screens.people

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.peopleScreen(onPersonClick: (String) -> Unit, modifier: Modifier = Modifier) {
    composable("people") {
        PeopleScreen(onPersonClick = onPersonClick, modifier = modifier)
    }
}