package ru.cringules.moodtool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.cringules.moodtool.ui.EntriesScreen
import ru.cringules.moodtool.ui.MoodScreen
import ru.cringules.moodtool.ui.theme.MoodToolTheme
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodToolTheme {
                App()
            }
        }
    }
}

@Composable
fun App(
    viewModel: MoodViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable(route = "main") {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(route = "new")
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                }
            ) { innerPadding ->
                EntriesScreen(
                    moodEntries = viewModel.state.moodEntries,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
        }

        composable(route = "new") {
            MoodScreen(
                viewModel = viewModel,
                onAdd = {
                    navController.popBackStack(route = "main", inclusive = false)
                }
            )
        }
    }
}
