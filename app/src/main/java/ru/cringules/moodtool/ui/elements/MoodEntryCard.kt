package ru.cringules.moodtool.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.ui.formatAngryAfraid
import ru.cringules.moodtool.ui.formatCheerfulDepressed
import ru.cringules.moodtool.ui.formatPressuredLonely
import ru.cringules.moodtool.ui.formatWillfulYielding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodEntryCard(
    moodEntry: MoodEntry,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    Card(onClick = onClick, modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = dateTimeFormatter.format(Instant.ofEpochMilli(moodEntry.timestamp.toEpochMilliseconds())),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = moodEntry.mood.formatAngryAfraid(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.mood.formatCheerfulDepressed(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.mood.formatWillfulYielding(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = moodEntry.mood.formatPressuredLonely(),
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