package ru.cringules.moodtool.ui.screens.tags

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.tagsScreen(modifier: Modifier = Modifier) {
    composable("tags") {
        TagsScreen(modifier = modifier)
    }
}