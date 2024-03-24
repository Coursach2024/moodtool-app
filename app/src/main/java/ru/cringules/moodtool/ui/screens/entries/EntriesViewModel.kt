package ru.cringules.moodtool.ui.screens.entries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.MyRecordRepository
import ru.cringules.moodtool.state.MoodEntriesState
import javax.inject.Inject


@HiltViewModel
class EntriesViewModel @Inject constructor(
    private val myRecordRepository: MyRecordRepository
) : ViewModel() {
    var entriesState: RepositoryResponse<List<MoodEntry>> by mutableStateOf(RepositoryResponse.Loading)
        private set

    init {
        loadMoodEntries()
    }

    fun loadMoodEntries() {
        viewModelScope.launch {
            entriesState = RepositoryResponse.Loading
            entriesState = myRecordRepository.getRecords()
        }
    }
}