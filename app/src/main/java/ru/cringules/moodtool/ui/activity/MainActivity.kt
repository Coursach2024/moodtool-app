package ru.cringules.moodtool.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.cringules.moodtool.ui.auth.AuthWrapper
import ru.cringules.moodtool.ui.theme.MoodToolTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodToolTheme {
                AuthWrapper(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

