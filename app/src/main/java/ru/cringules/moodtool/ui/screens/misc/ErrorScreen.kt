package ru.cringules.moodtool.ui.screens.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.cringules.moodtool.ui.theme.MoodToolTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier,
                canRetry: Boolean = false,
                onRetry: () -> Unit = {}) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Sharp.CloudOff,
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Text(text = "Failed to load")
            if (canRetry) {
                Button(onClick = onRetry) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    MoodToolTheme {
        ErrorScreen(canRetry = true)
    }
}
