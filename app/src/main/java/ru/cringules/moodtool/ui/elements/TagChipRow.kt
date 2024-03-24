package ru.cringules.moodtool.ui.elements

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagChipRow(tags: List<String>, modifier: Modifier = Modifier) {
    FlowRow(modifier = modifier) {
        tags.forEach {
            InputChip(
                selected = false,
                onClick = { },
                label = {
                    Text(text = it)
                },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}