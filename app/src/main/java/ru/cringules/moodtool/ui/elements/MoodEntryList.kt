package ru.cringules.moodtool.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cringules.moodtool.data.model.MoodEntry

@Composable
fun MoodEntryList(
    entries: List<MoodEntry>,
    onEditEntry: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(entries) { entry ->
            MoodEntryCard(
                moodEntry = entry,
                onClick = {
                    entry.id?.let(onEditEntry)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}