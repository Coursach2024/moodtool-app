package ru.cringules.moodtool.ui.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.cringules.moodtool.ui.screens.entries.editEntryScreen
import ru.cringules.moodtool.ui.screens.entries.entriesScreen
import ru.cringules.moodtool.ui.screens.entries.navigateToEditEntryScreen
import ru.cringules.moodtool.ui.screens.entries.navigateToEntriesScreen
import ru.cringules.moodtool.ui.screens.entries.navigateToNewEntryScreen
import ru.cringules.moodtool.ui.screens.entries.newEntryScreen
import ru.cringules.moodtool.ui.screens.people.navigateToPersonScreen
import ru.cringules.moodtool.ui.screens.people.peopleScreen
import ru.cringules.moodtool.ui.screens.people.personScreen
import ru.cringules.moodtool.ui.screens.settings.settingsScreen
import ru.cringules.moodtool.ui.screens.share.shareScreen
import ru.cringules.moodtool.ui.screens.tags.tagsScreen

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val pages = listOf(
        MainScreenPage.Entries,
        MainScreenPage.Tags,
        MainScreenPage.People,
        MainScreenPage.Share
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopAppBar(onSettingsClicked = {
                navController.navigate(route = "settings") {
                    viewModel.page = "settings"
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        },
        bottomBar = {
            MainBottomAppBar(
                pages = pages,
                selectedRoute = viewModel.page,
                onPageClicked = {
                    viewModel.page = it.route
                    navController.navigate(route = it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        Log.d(null, it.toString())
        MainNavHost(
            navController = navController,
            onLogout = onLogout,
            modifier = Modifier
                .padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(onSettingsClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "MoodTool")
        },
        actions = {
            IconButton(onClick = onSettingsClicked) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "")
            }
        }
    )
}

@Composable
fun MainBottomAppBar(
    pages: List<MainScreenPage>,
    selectedRoute: String,
    onPageClicked: (MainScreenPage) -> Unit
) {
    NavigationBar {
        pages.forEach { page ->
            NavigationBarItem(
                selected = page.route == selectedRoute,
                onClick = {
                    onPageClicked(page)
                },
                icon = {
                    val icon = if (page.route == selectedRoute) page.iconSelected else page.icon
                    Icon(imageVector = icon, contentDescription = page.label)
                },
                label = {
                    Text(text = page.label, style = MaterialTheme.typography.labelMedium)
                })
        }
    }
}

@Composable
fun MainNavHost(navController: NavHostController, onLogout: () -> Unit, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainScreenPage.Entries.route,
        modifier = modifier
    ) {
        entriesScreen(
            onNewEntry = {
                navController.navigateToNewEntryScreen()
            },
            onEditEntry = {
                navController.navigateToEditEntryScreen(it)
            }
        )
        newEntryScreen(
            onExit = {
                navController.navigateToEntriesScreen()
            }
        )
        editEntryScreen(
            onSaved = {
                navController.navigateToEntriesScreen()
            }
        )

        tagsScreen()

        peopleScreen(onPersonClick = {
            navController.navigateToPersonScreen(it)
        })
        personScreen()

        shareScreen()
        settingsScreen(onLogout = onLogout)
    }
}