package ru.cringules.moodtool.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainScreenPage(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector
) {
    object Entries : MainScreenPage(
        route = "entries",
        label = "Entries",
        icon = Icons.Outlined.Mood,
        iconSelected = Icons.Filled.Mood
    )

    object Tags : MainScreenPage(
        route = "tags",
        label = "Tags",
        icon = Icons.Outlined.Timeline,
        iconSelected = Icons.Filled.Timeline
    )

    object People : MainScreenPage(
        route = "people",
        label = "People",
        icon = Icons.Outlined.Groups,
        iconSelected = Icons.Filled.Groups
    )

    object Share : MainScreenPage(
        route = "share",
        label = "Share",
        icon = Icons.Outlined.Share,
        iconSelected = Icons.Filled.Share
    )
}