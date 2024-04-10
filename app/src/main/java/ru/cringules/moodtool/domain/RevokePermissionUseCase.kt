package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.PermissionUserRequest
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.PermissionRepository
import javax.inject.Inject

class RevokePermissionUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository
) {
    suspend operator fun invoke(username: String): RepositoryResponse<Unit> {
        return permissionRepository.recallPermission(PermissionUserRequest(username))
    }
}