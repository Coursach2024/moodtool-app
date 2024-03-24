package ru.cringules.moodtool.ui.screens.share

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cringules.moodtool.data.model.PermissionUserRequest
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.PermissionRepository
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val permissionRepository: PermissionRepository
) : ViewModel() {
    var usersState: RepositoryResponse<List<String>> by mutableStateOf(RepositoryResponse.Loading)
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            usersState = RepositoryResponse.Loading
            usersState = permissionRepository.getRecipients()
        }
    }

    fun removeUser(username: String) {
        viewModelScope.launch {
            permissionRepository.recallPermission(PermissionUserRequest(username))
            loadUsers()
        }
    }

    fun addUser(username: String) {
        viewModelScope.launch {
            permissionRepository.issuePermission(PermissionUserRequest(username))
            loadUsers()
        }
    }
}