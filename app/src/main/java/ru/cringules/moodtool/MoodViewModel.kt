package ru.cringules.moodtool

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import ru.cringules.moodtool.data.MoodEntriesApiRepository

data class MoodUiState(
    val newMoodEntry: MoodEntry = MoodEntry(),
    val moodEntries: List<MoodEntry> = listOf()
)

class MoodViewModel : ViewModel() {
    var state: MoodUiState by mutableStateOf(MoodUiState())
        private set

    private val moodEntriesRepository = MoodEntriesApiRepository()

    init {
        getMoodEntries()
    }

    private fun getMoodEntries() {
        viewModelScope.launch {
            state = state.copy(moodEntries = moodEntriesRepository.getMoodEntries())
        }
    }

    fun resetNewMoodEntry() {
        state = state.copy(newMoodEntry = MoodEntry())
    }

    fun updateNewMoodEntry(moodEntry: MoodEntry) {
        state = state.copy(newMoodEntry = moodEntry)
    }

    fun createMoodEntry() {
        viewModelScope.launch {
            moodEntriesRepository.createMoodEntry(state.newMoodEntry)
            state = state.copy(moodEntries = moodEntriesRepository.getMoodEntries())
        }
    }
}