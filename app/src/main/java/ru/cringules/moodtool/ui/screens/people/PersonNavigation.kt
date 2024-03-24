package ru.cringules.moodtool.ui.screens.people

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.personScreen(modifier: Modifier = Modifier) {
    composable("people/{username}") {
        PersonScreen(modifier = modifier)
    }
}

fun NavController.navigateToPersonScreen(username: String) {
    navigate("people/$username")
}