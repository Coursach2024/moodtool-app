package ru.cringules.moodtool.ui.screens.share

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.shareScreen(modifier: Modifier = Modifier) {
    composable("share") {
        ShareScreen(modifier = modifier)
    }
}