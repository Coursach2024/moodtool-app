package ru.cringules.moodtool.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
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
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.cringules.moodtool.model.Mood
import ru.cringules.moodtool.model.MoodEntry
import ru.cringules.moodtool.PredictedMoodState
import ru.cringules.moodtool.ui.theme.MoodToolTheme
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NewEntryScreen(
    moodEntry: MoodEntry,
    prediction: PredictedMoodState,
    onMoodEntryChange: (MoodEntry) -> Unit = {},
    onAdd: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val dateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MoodTool")
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        var dateDialogShown by remember {
            mutableStateOf(false)
        }
        var timeDialogShown by remember {
            mutableStateOf(false)
        }

        val localDateTime =
            moodEntry.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = moodEntry.timestamp.toEpochMilliseconds(),
            initialDisplayedMonthMillis = moodEntry.timestamp.toEpochMilliseconds()
        )

        val timePickerState = rememberTimePickerState(
            initialHour = localDateTime.hour,
            initialMinute = localDateTime.minute
        )

        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Date",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = dateTimeFormatter.format(java.time.Instant.ofEpochMilli(moodEntry.timestamp.toEpochMilliseconds()))
                        )
                    }
                    Button(onClick = { dateDialogShown = true }) {
                        Text(text = "Select")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tags",
                    style = MaterialTheme.typography.headlineSmall
                )
                FlowRow {
                    moodEntry.tags.forEach {
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
                        onMoodEntryChange(moodEntry.copy(tags = moodEntry.tags + text))
                        text = ""
                    }) {
                        Text(text = "Add tag")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Prediction",
                    style = MaterialTheme.typography.headlineSmall
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.height(100.dp)
                ) {
                    when (prediction) {
                        is PredictedMoodState.Loading -> {
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        is PredictedMoodState.Success -> {
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
                        }

                        is PredictedMoodState.Error -> {

                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mood",
                    style = MaterialTheme.typography.headlineSmall
                )
                MoodSlider(
                    value = moodEntry.mood.angryAfraid.toFloat(),
                    positiveLabel = "Angry",
                    negativeLabel = "Afraid",
                    onValueChange = {
                        onMoodEntryChange(moodEntry.copy(mood = moodEntry.mood.copy(angryAfraid = it.toInt())))
                    }
                )
                MoodSlider(
                    value = moodEntry.mood.cheerfulDepressed.toFloat(),
                    positiveLabel = "Cheerful",
                    negativeLabel = "Depressed",
                    onValueChange = {
                        onMoodEntryChange(moodEntry.copy(mood = moodEntry.mood.copy(cheerfulDepressed = it.toInt())))
                    }
                )
                MoodSlider(
                    value = moodEntry.mood.willfulYielding.toFloat(),
                    positiveLabel = "Willful",
                    negativeLabel = "Yielding",
                    onValueChange = {
                        onMoodEntryChange(moodEntry.copy(mood = moodEntry.mood.copy(willfulYielding = it.toInt())))
                    }
                )
                MoodSlider(
                    value = moodEntry.mood.pressuredLonely.toFloat(),
                    positiveLabel = "Pressured",
                    negativeLabel = "Lonely",
                    onValueChange = {
                        onMoodEntryChange(moodEntry.copy(mood = moodEntry.mood.copy(pressuredLonely = it.toInt())))
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))


//            val chipState = rememberChipTextFieldState<Chip>()
//            OutlinedChipTextField(state = chipState, onSubmit = { Chip(it) })
                Button(onClick = onAdd) {
                    Text(text = "Add")
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
                        onMoodEntryChange(
                            moodEntry.copy(
                                timestamp = Instant.fromEpochMilliseconds(millis)
                                    .plus(timePickerState.hour.hours)
                                    .plus(timePickerState.minute.minutes)
                            )
                        )
                        timeDialogShown = false
                    }
                }) {
                    TimePicker(state = timePickerState)
                }
            }
        }
    }
}

@Composable
fun MoodSlider(
    value: Float,
    positiveLabel: String,
    negativeLabel: String,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = negativeLabel)
            Text(text = "${if (value < 0) negativeLabel else positiveLabel} ${abs(value.roundToInt())}")
            Text(text = positiveLabel)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = -5f..5f,
            steps = 9
        )
    }
}

@Preview
@Composable
fun NewEntryScreenPreview() {
    MoodToolTheme(darkTheme = true, dynamicColor = false) {
        NewEntryScreen(
            moodEntry = MoodEntry(),
            prediction = PredictedMoodState.Success(Mood(0, 0, 0, 0)),
            modifier = Modifier.fillMaxSize()
        )
    }
}
