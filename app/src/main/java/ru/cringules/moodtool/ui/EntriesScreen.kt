package ru.cringules.moodtool.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import ru.cringules.moodtool.model.Mood
import ru.cringules.moodtool.model.MoodEntry
import ru.cringules.moodtool.ui.theme.MoodToolTheme
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesScreen(
    moodEntries: List<MoodEntry>,
    onNewEntry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MoodTool")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNewEntry
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Add")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(moodEntries) { entry ->
                MoodCard(
                    moodEntry = entry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodCard(
    moodEntry: MoodEntry,
    modifier: Modifier = Modifier
) {
    val dateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = dateTimeFormatter.format(java.time.Instant.ofEpochMilli(moodEntry.timestamp.toEpochMilliseconds())),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = moodEntry.formatAngryAfraid(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.formatCheerfulDepressed(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.formatWillfulYielding(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.formatPressuredLonely(),
                style = MaterialTheme.typography.bodyLarge
            )
            FlowRow {
                moodEntry.tags.forEach {
                    SuggestionChip(
                        onClick = { },
                        label = {
                            Text(text = it)
                        },
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EntriesScreenPreview() {
    MoodToolTheme {
        EntriesScreen(
            moodEntries = listOf(
                MoodEntry(
                    null, Mood(),
                    Clock.System.now(),
                    listOf("12", "2323422")
                ),
                MoodEntry(
                    null, Mood(),
                    Clock.System.now(),
                    listOf("124", "2323422")
                )
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}