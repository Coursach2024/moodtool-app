package ru.cringules.moodtool.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dokar.chiptextfield.Chip
import com.dokar.chiptextfield.ChipTextField
import com.dokar.chiptextfield.OutlinedChipTextField
import com.dokar.chiptextfield.rememberChipTextFieldState
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.cringules.moodtool.MoodViewModel
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
fun MoodScreen(
    viewModel: MoodViewModel = viewModel(),
    onAdd: () -> Unit
) {
    val dateTimeFormatter = DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    Scaffold { paddingValues ->
        var dateDialogShown by remember {
            mutableStateOf(false)
        }
        var timeDialogShown by remember {
            mutableStateOf(false)
        }

        val localDateTime =
            viewModel.state.newMoodEntry.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = viewModel.state.newMoodEntry.timestamp.toEpochMilliseconds(),
            initialDisplayedMonthMillis = viewModel.state.newMoodEntry.timestamp.toEpochMilliseconds()
        )

        val timePickerState = rememberTimePickerState(
            initialHour = localDateTime.hour,
            initialMinute = localDateTime.minute
        )

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(
                text = dateTimeFormatter.format(java.time.Instant.ofEpochMilli(viewModel.state.newMoodEntry.timestamp.toEpochMilliseconds())),
                style = MaterialTheme.typography.labelMedium
            )
            Button(onClick = { dateDialogShown = true }) {
                Text(text = "Select date and time")
            }
            MoodSlider(
                value = viewModel.state.newMoodEntry.angryAfraid.toFloat(),
                positiveLabel = "Angry",
                negativeLabel = "Afraid",
                onValueChange = {
                    viewModel.updateNewMoodEntry(viewModel.state.newMoodEntry.copy(angryAfraid = it.toInt()))
                }
            )
            MoodSlider(
                value = viewModel.state.newMoodEntry.cheerfulDepressed.toFloat(),
                positiveLabel = "Cheerful",
                negativeLabel = "Depressed",
                onValueChange = {
                    viewModel.updateNewMoodEntry(viewModel.state.newMoodEntry.copy(cheerfulDepressed = it.toInt()))
                }
            )
            MoodSlider(
                value = viewModel.state.newMoodEntry.willfulYielding.toFloat(),
                positiveLabel = "Willful",
                negativeLabel = "Yielding",
                onValueChange = {
                    viewModel.updateNewMoodEntry(viewModel.state.newMoodEntry.copy(willfulYielding = it.toInt()))
                }
            )
            MoodSlider(
                value = viewModel.state.newMoodEntry.pressuredLonely.toFloat(),
                positiveLabel = "Pressured",
                negativeLabel = "Lonely",
                onValueChange = {
                    viewModel.updateNewMoodEntry(viewModel.state.newMoodEntry.copy(pressuredLonely = it.toInt()))
                }
            )

            FlowRow {
                viewModel.state.newMoodEntry.tags.forEach {
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
            TextField(value = text, onValueChange = { text = it })
            Button(onClick = {
                viewModel.updateNewMoodEntry(viewModel.state.newMoodEntry.copy(tags = viewModel.state.newMoodEntry.tags + text))
            }) {

            }

//            val chipState = rememberChipTextFieldState<Chip>()
//            OutlinedChipTextField(state = chipState, onSubmit = { Chip(it) })
            Button(onClick = {
                viewModel.createMoodEntry()
                onAdd()
            }) {
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
                    viewModel.updateNewMoodEntry(
                        viewModel.state.newMoodEntry.copy(
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