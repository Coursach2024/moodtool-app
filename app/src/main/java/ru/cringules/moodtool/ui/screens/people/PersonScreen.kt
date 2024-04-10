package ru.cringules.moodtool.ui.screens.people

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cringules.moodtool.R
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCorrelations
import ru.cringules.moodtool.ui.elements.MoodEntryList
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen
import kotlin.math.abs

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val correlationsState = viewModel.correlationsState
    val entriesState = viewModel.entriesState

    when {
        correlationsState == RepositoryResponse.Loading || entriesState == RepositoryResponse.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        entriesState is RepositoryResponse.Success -> {
            val correlations = if (correlationsState is RepositoryResponse.Success) {
                correlationsState.data
            } else null
            PersonContent(
                correlations = correlations,
                entries = entriesState.data,
                modifier = modifier
            )
        }

        else -> {
            ErrorScreen(
                modifier = modifier,
                canRetry = true,
                onRetry = {
                    viewModel.loadCorrelations()
                    viewModel.loadEntries()
                }
            )
        }
    }
}

@Composable
fun PersonContent(
    correlations: UserCorrelations?,
    entries: List<MoodEntry>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        UserCorrelationDisplay(
            correlations = correlations,
            modifier = Modifier.fillMaxWidth()
        )
        MoodEntryList(
            entries = entries,
            onEditEntry = {}
        )
    }
}

@Composable
fun UserCorrelationDisplay(
    correlations: UserCorrelations?,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        if (correlations == null) {
            Text(
                text = "Not available",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
        } else {
            CorrelationTable(
                correlations = correlations,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun CorrelationTable(
    correlations: UserCorrelations,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 2.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            AxisLabel(
                negativeLabel = "Angry",
                positiveLabel = "Afraid",
                modifier = Modifier.weight(1f)
            )
            AxisLabel(
                negativeLabel = "Cheerful",
                positiveLabel = "Depressed",
                modifier = Modifier.weight(1f)
            )
            AxisLabel(
                negativeLabel = "Willful",
                positiveLabel = "Yielding",
                modifier = Modifier.weight(1f)
            )
            AxisLabel(
                negativeLabel = "Pressured",
                positiveLabel = "Lonely",
                modifier = Modifier.weight(1f)
            )
        }
        CorrelationRow(
            negativeLabel = "Angry",
            positiveLabel = "Afraid",
            correlations = correlations.angryAfraid,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        CorrelationRow(
            negativeLabel = "Cheerful",
            positiveLabel = "Depressed",
            correlations = correlations.cheerfulDepressed,
            modifier = Modifier.padding(vertical = 2.dp)

        )
        CorrelationRow(
            negativeLabel = "Willful",
            positiveLabel = "Yielding",
            correlations = correlations.willfulYielding,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        CorrelationRow(
            negativeLabel = "Pressured",
            positiveLabel = "Lonely",
            correlations = correlations.pressuredLonely,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
fun CorrelationRow(
    negativeLabel: String,
    positiveLabel: String,
    correlations: Correlations,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        AxisLabel(
            negativeLabel = negativeLabel,
            positiveLabel = positiveLabel,
            modifier = Modifier.weight(1f)
        )
        CorrelationIcon(
            value = correlations.angryAfraid,
            modifier = Modifier.weight(1f)
        )
        CorrelationIcon(
            value = correlations.cheerfulDepressed,
            modifier = Modifier.weight(1f)
        )
        CorrelationIcon(
            value = correlations.willfulYielding,
            modifier = Modifier.weight(1f)
        )
        CorrelationIcon(
            value = correlations.pressuredLonely,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AxisLabel(
    negativeLabel: String,
    positiveLabel: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = positiveLabel,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = negativeLabel,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun CorrelationIcon(
    value: Double,
    modifier: Modifier = Modifier
) {
    val iconId = when {
        value <= -0.5 -> R.drawable.ic_stat_minus_3_24
        value <= -0.3 -> R.drawable.ic_stat_minus_2_24
        value <= -0.1 -> R.drawable.ic_stat_minus_1_24
        value < 0.1 -> R.drawable.ic_stat_0_24
        value < 0.3 -> R.drawable.ic_stat_1_24
        value < 0.5 -> R.drawable.ic_stat_2_24
        else -> R.drawable.ic_stat_3_24
    }
    Icon(painter = painterResource(id = iconId), contentDescription = "", modifier = modifier)
}

@Preview
@Composable
private fun PersonScreenPreview() {
    PersonScreen(modifier = Modifier.fillMaxSize())
}