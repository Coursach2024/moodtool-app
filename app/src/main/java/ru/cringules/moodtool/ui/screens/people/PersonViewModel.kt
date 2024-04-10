package ru.cringules.moodtool.ui.screens.people

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCorrelations
import ru.cringules.moodtool.domain.GetUserCorrelationsUseCase
import ru.cringules.moodtool.domain.ListUserEntriesUseCase
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getUserCorrelationsUseCase: GetUserCorrelationsUseCase,
    private val listUserEntriesUseCase: ListUserEntriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val username: String? = savedStateHandle["username"]

    var correlationsState: RepositoryResponse<UserCorrelations> by mutableStateOf(RepositoryResponse.Loading)
        private set

    var entriesState: RepositoryResponse<List<MoodEntry>> by mutableStateOf(RepositoryResponse.Loading)
        private set

    init {
        loadCorrelations()
        loadEntries()
    }

    fun loadCorrelations() {
        viewModelScope.launch {
            correlationsState = RepositoryResponse.Loading
            username?.let {
                correlationsState = getUserCorrelationsUseCase(it)
            }
        }
    }

    fun loadEntries() {
        viewModelScope.launch {
            entriesState = RepositoryResponse.Loading
            username?.let {
                entriesState = listUserEntriesUseCase(it)
            }
        }
    }
}