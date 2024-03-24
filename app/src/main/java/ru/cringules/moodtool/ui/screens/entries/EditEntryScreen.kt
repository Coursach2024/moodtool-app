package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.ui.TimePickerDialog
import ru.cringules.moodtool.ui.elements.TagChipRow
import ru.cringules.moodtool.ui.formatAngryAfraid
import ru.cringules.moodtool.ui.formatCheerfulDepressed
import ru.cringules.moodtool.ui.formatPressuredLonely
import ru.cringules.moodtool.ui.formatWillfulYielding
import ru.cringules.moodtool.ui.screens.misc.ErrorScreen
import ru.cringules.moodtool.ui.screens.misc.LoadingScreen
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Composable
fun EditEntryScreen(
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditEntryViewModel = hiltViewModel()
) {
    when (val state = viewModel.entryState) {
        is RepositoryResponse.Success -> {
            EditEntryContent(
                entry = state.data,
                onConditionsChange = {
                    viewModel.onConditionsChange(it)
                },
                onMoodChange = {
                    viewModel.onMoodChange(it)
                },
                predictedMoodState = viewModel.predictedMoodState,
                onSave = {
                    viewModel.save()
                    onSaved()
                },
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
fun EditEntryContent(
    entry: MoodEntry,
    onConditionsChange: (MoodEntry) -> Unit,
    onMoodChange: (Mood) -> Unit,
    predictedMoodState: RepositoryResponse<Mood>,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        ConditionsCard(
            timestamp = entry.timestamp,
            onTimestampChange = {
                onConditionsChange(entry.copy(timestamp = it))
            },
            tags = entry.tags,
            onTagAdd = {
                onConditionsChange(entry.copy(tags = entry.tags + it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PredictionCard(
            predictedMoodState = predictedMoodState,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        MoodEditCard(
            mood = entry.mood,
            onMoodChange = {
                onMoodChange(it)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSave) {
            Text(text = "Add")
        }
    }
}

@Composable
fun ConditionsCard(
    timestamp: Instant,
    onTimestampChange: (Instant) -> Unit,
    tags: List<String>,
    onTagAdd: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        DateTimeSelector(timestamp = timestamp, onTimestampChange = onTimestampChange)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tags",
            style = MaterialTheme.typography.headlineSmall
        )
        TagChipRow(tags = tags)

        var text by remember {
            mutableStateOf("")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(text = "Tag")
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = {
                onTagAdd(text)
                text = ""
            }) {
                Text(text = "Add tag")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSelector(
    timestamp: Instant,
    onTimestampChange: (Instant) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    var dateDialogShown by remember {
        mutableStateOf(false)
    }
    var timeDialogShown by remember {
        mutableStateOf(false)
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = timestamp.toEpochMilliseconds(),
        initialDisplayedMonthMillis = timestamp.toEpochMilliseconds()
    )

    val localDateTime = timestamp.toLocalDateTime(TimeZone.currentSystemDefault())

    val timePickerState = rememberTimePickerState(
        initialHour = localDateTime.hour,
        initialMinute = localDateTime.minute
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column {
            Text(
                text = "Date",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = dateTimeFormatter.format(java.time.Instant.ofEpochMilli(timestamp.toEpochMilliseconds()))
            )
        }
        Button(onClick = { dateDialogShown = true }) {
            Text(text = "Select")
        }
    }

    if (dateDialogShown) {
        DatePickerDialog(onDismissRequest = { dateDialogShown = false }, confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                if (millis != null) {
                    dateDialogShown = false
                    timeDialogShown = true
                }
            }) {
                Text(text = "Yay")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }

    if (timeDialogShown) {
        TimePickerDialog(onDismissRequest = { timeDialogShown = false }, onConfirm = {
            val millis = datePickerState.selectedDateMillis
            if (millis != null) {
                val newTimestamp = Instant.fromEpochMilliseconds(millis)
                    .plus(timePickerState.hour.hours)
                    .plus(timePickerState.minute.minutes)
                onTimestampChange(newTimestamp)
                timeDialogShown = false
            }
        }) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun PredictionCard(
    predictedMoodState: RepositoryResponse<Mood>,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Text(
            text = "Prediction",
            style = MaterialTheme.typography.headlineSmall
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(100.dp)
        ) {
            when (predictedMoodState) {
                RepositoryResponse.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                is RepositoryResponse.Success -> {
                    Text(
                        text = predictedMoodState.data.formatAngryAfraid(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = predictedMoodState.data.formatCheerfulDepressed(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = predictedMoodState.data.formatWillfulYielding(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = predictedMoodState.data.formatPressuredLonely(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    ErrorScreen()
                }
            }
        }
    }
}

@Composable
fun MoodEditCard(
    mood: Mood,
    onMoodChange: (Mood) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Text(
            text = "Mood",
            style = MaterialTheme.typography.headlineSmall
        )
        MoodSlider(
            value = mood.angryAfraid,
            positiveLabel = "Angry",
            negativeLabel = "Afraid",
            onValueChange = {
                onMoodChange(mood.copy(angryAfraid = it.toInt()))
            }
        )
        MoodSlider(
            value = mood.cheerfulDepressed,
            positiveLabel = "Cheerful",
            negativeLabel = "Depressed",
            onValueChange = {
                onMoodChange(mood.copy(cheerfulDepressed = it.toInt()))
            }
        )
        MoodSlider(
            value = mood.willfulYielding,
            positiveLabel = "Willful",
            negativeLabel = "Yielding",
            onValueChange = {
                onMoodChange(mood.copy(willfulYielding = it.toInt()))
            }
        )
        MoodSlider(
            value = mood.pressuredLonely,
            positiveLabel = "Pressured",
            negativeLabel = "Lonely",
            onValueChange = {
                onMoodChange(mood.copy(pressuredLonely = it.toInt()))
            }
        )
    }
}

@Composable
fun MoodSlider(
    value: Int,
    positiveLabel: String,
    negativeLabel: String,
    onValueChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = negativeLabel)
            Text(text = "${if (value < 0) negativeLabel else positiveLabel} ${abs(value)}")
            Text(text = positiveLabel)
        }
        Slider(
            value = value.toFloat(),
            onValueChange = {
                onValueChange(it.roundToInt())
            },
            valueRange = -5f..5f,
            steps = 9
        )
    }
}

@Preview
@Composable
private fun EditEntryScreenPreview() {
    EditEntryScreen(onSaved = {}, modifier = Modifier.fillMaxSize())
}