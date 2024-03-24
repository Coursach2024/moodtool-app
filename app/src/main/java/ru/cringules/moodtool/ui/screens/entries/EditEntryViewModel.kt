package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.dto.ApiResponse
import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodConditions
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.AnalyticsRepository
import ru.cringules.moodtool.data.repository.MyRecordRepository
import javax.inject.Inject

sealed interface PredictedMoodState {
    data class Success(val mood: Mood) : PredictedMoodState
    object Loading : PredictedMoodState
    object Error : PredictedMoodState
}

@HiltViewModel
class EditEntryViewModel @Inject constructor(
    private val myRecordRepository: MyRecordRepository,
    private val analyticsRepository: AnalyticsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val entryId: Int? = savedStateHandle["entryId"]

    var entryState: RepositoryResponse<MoodEntry> by mutableStateOf(RepositoryResponse.Loading)
        private set

    var predictedMoodState: RepositoryResponse<Mood> by mutableStateOf(RepositoryResponse.Loading)
        private set

    private var predictJob: Job? = null

    init {
        loadEntry()
    }

    private fun loadEntry() {
        viewModelScope.launch {
            if (entryId == null) {
                entryState = RepositoryResponse.Success(MoodEntry())
                return@launch
            }

            entryState = myRecordRepository.getRecord(entryId)
            val state = entryState
            if (state is RepositoryResponse.Success) {
                predict(state.data)
            }
        }
    }

    private fun predict(moodEntry: MoodEntry) {
        predictJob?.cancel()
        predictJob = viewModelScope.launch {
            predictedMoodState = RepositoryResponse.Loading
            predictedMoodState = analyticsRepository.predictMood(
                MoodConditions(
                    timestamp = moodEntry.timestamp,
                    tags = moodEntry.tags
                )
            )
        }
    }

    fun onConditionsChange(moodEntry: MoodEntry) {
        entryState = RepositoryResponse.Success(moodEntry)
        predict(moodEntry)
    }

    fun onMoodChange(mood: Mood) {
        val state = entryState
        if (state is RepositoryResponse.Success) {
            entryState = RepositoryResponse.Success(state.data.copy(mood = mood))
        }
    }

    fun save() {
        viewModelScope.launch {
            val state = entryState
            if (state is RepositoryResponse.Success) {
                val result = if (entryId == null) {
                    myRecordRepository.createMoodEntry(state.data)
                } else {
                    myRecordRepository.updateRecord(entryId, state.data)
                }
            }
        }
    }
}