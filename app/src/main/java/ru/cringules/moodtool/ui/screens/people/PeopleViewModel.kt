package ru.cringules.moodtool.ui.screens.people

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.PermissionRepository
import ru.cringules.moodtool.domain.ListAvailableUsersUseCase
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val listAvailableUsersUseCase: ListAvailableUsersUseCase
) : ViewModel() {
    var peopleState: RepositoryResponse<List<String>> by mutableStateOf(RepositoryResponse.Loading)
        private set

    init {
        loadPeople()
    }

    fun loadPeople() {
        viewModelScope.launch {
            peopleState = RepositoryResponse.Loading
            peopleState = listAvailableUsersUseCase()
        }
    }
}