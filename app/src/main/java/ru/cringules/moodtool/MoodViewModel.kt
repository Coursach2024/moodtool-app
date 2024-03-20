package ru.cringules.moodtool

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.MoodEntriesRepository
import ru.cringules.moodtool.model.Mood
import ru.cringules.moodtool.model.MoodConditions
import ru.cringules.moodtool.model.MoodEntry
import javax.inject.Inject

data class MoodUiState(
    val newMoodEntry: MoodEntry = MoodEntry(),
    val moodEntries: List<MoodEntry> = listOf()
)

sealed interface PredictedMoodState {
    class Success(mood: Mood) : PredictedMoodState
    object Loading : PredictedMoodState
    object Error : PredictedMoodState
}

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val moodEntriesRepository: MoodEntriesRepository
) : ViewModel() {
    var loadingState: MoodEntriesState by mutableStateOf(MoodEntriesState.Loading)
        private set

    var state: MoodUiState by mutableStateOf(MoodUiState())
        private set

    var predictedMoodState: PredictedMoodState by mutableStateOf(PredictedMoodState.Loading)
    var predictJob: Job? = null

    init {
        viewModelScope.launch {
            getMoodEntries()
        }
    }

    private suspend fun getMoodEntries() {
        loadingState = MoodEntriesState.Loading
        loadingState = moodEntriesRepository.getMoodEntries()
    }

    private suspend fun predictMoodState() {
        predictedMoodState = PredictedMoodState.Loading
        predictedMoodState = moodEntriesRepository.predictMood(
            MoodConditions(
                state.newMoodEntry.timestamp,
                state.newMoodEntry.tags
            )
        )
    }

    fun resetNewMoodEntry() {
        state = state.copy(newMoodEntry = MoodEntry())
        predictJob = viewModelScope.launch {
            predictMoodState()
        }
    }

    fun updateNewMoodEntry(moodEntry: MoodEntry) {
        state = state.copy(newMoodEntry = moodEntry)
        predictJob?.cancel()
        predictJob = viewModelScope.launch {
            predictMoodState()
        }
    }

    fun createMoodEntry() {
        predictJob?.cancel()
        predictJob = null
        viewModelScope.launch {
            moodEntriesRepository.createMoodEntry(state.newMoodEntry)
            getMoodEntries()
        }
    }
}