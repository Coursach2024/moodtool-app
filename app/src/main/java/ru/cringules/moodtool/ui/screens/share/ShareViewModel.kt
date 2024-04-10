package ru.cringules.moodtool.ui.screens.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.domain.GivePermissionUseCase
import ru.cringules.moodtool.domain.ListPermittedUsersUseCase
import ru.cringules.moodtool.domain.RevokePermissionUseCase
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val listPermittedUsersUseCase: ListPermittedUsersUseCase,
    private val givePermissionUseCase: GivePermissionUseCase,
    private val revokePermissionUseCase: RevokePermissionUseCase
) : ViewModel() {
    var usersState: RepositoryResponse<List<String>> by mutableStateOf(RepositoryResponse.Loading)
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            usersState = RepositoryResponse.Loading
            usersState = listPermittedUsersUseCase()
        }
    }

    fun removeUser(username: String) {
        viewModelScope.launch {
            revokePermissionUseCase(username)
            loadUsers()
        }
    }

    fun addUser(username: String) {
        viewModelScope.launch {
            givePermissionUseCase(username)
            loadUsers()
        }
    }
}