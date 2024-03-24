package ru.cringules.moodtool.ui.screens.settings

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.settingsScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    composable("settings") {
        SettingsScreen(
            onLogout = onLogout,
            modifier = modifier
        )
    }
}