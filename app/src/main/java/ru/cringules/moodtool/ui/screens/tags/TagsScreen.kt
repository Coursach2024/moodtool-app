package ru.cringules.moodtool.ui.screens.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.R
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen
import kotlin.math.abs

@Composable
fun TagsScreen(
    modifier: Modifier = Modifier,
    viewModel: TagsViewModel = hiltViewModel()
) {
    when (val state = viewModel.tagsCorrelationsState) {
        is RepositoryResponse.Success -> {
            TagList(
                entries = state.data,
                modifier = modifier
            )
        }
        is RepositoryResponse.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        else -> {
            ErrorScreen(modifier = modifier)
        }
    }
}

@Composable
fun TagList(entries: Map<String, Correlations>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(
            entries.entries.toList(),
            key = { it.key }
        ) { entry ->
            TagCard(
                tag = entry.key,
                tagCorrelations = entry.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun TagCard(tag: String, tagCorrelations: Correlations, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = tag,
                style = MaterialTheme.typography.titleLarge
            )
            Row(modifier = Modifier.padding(top = 5.dp)) {
                CorrelationIndicator(
                    value = tagCorrelations.angryAfraid,
                    negativeLabel = "Angry",
                    positiveLabel = "Afraid",
                    modifier = Modifier.weight(1f)
                )
                CorrelationIndicator(
                    value = tagCorrelations.cheerfulDepressed,
                    negativeLabel = "Cheerful",
                    positiveLabel = "Depressed",
                    modifier = Modifier.weight(1f)
                )
                CorrelationIndicator(
                    value = tagCorrelations.willfulYielding,
                    negativeLabel = "Willful",
                    positiveLabel = "Yielding",
                    modifier = Modifier.weight(1f)
                )
                CorrelationIndicator(
                    value = tagCorrelations.pressuredLonely,
                    negativeLabel = "Pressured",
                    positiveLabel = "Lonely",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CorrelationIndicator(
    value: Double,
    negativeLabel: String,
    positiveLabel: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val absValue = abs(value)
        val iconId = when {
            absValue < 0.1 -> R.drawable.ic_stat_0_24
            absValue < 0.3 -> R.drawable.ic_stat_1_24
            absValue < 0.5 -> R.drawable.ic_stat_2_24
            else -> R.drawable.ic_stat_3_24
        }
        val label = when {
            absValue < 0.1 -> ""
            value > 0 -> positiveLabel
            else -> negativeLabel
        }

        Icon(painter = painterResource(id = iconId), contentDescription = "")
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun TagsScreenPreview() {
    TagList(
        entries = mapOf(
            Pair(
                "tag1",
                Correlations(
                    angryAfraid = 0.1,
                    cheerfulDepressed = -0.4,
                    willfulYielding = 0.33,
                    pressuredLonely = 0.0
                )
            )
        ),
        modifier = Modifier.fillMaxSize()
    )
}