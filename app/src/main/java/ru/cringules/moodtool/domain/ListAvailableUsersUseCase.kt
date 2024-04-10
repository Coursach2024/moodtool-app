package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.PermissionRepository
import javax.inject.Inject

class ListAvailableUsersUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository
) {
    suspend operator fun invoke(offset: Int = 0, limit: Int = 100): RepositoryResponse<List<String>> {
        return permissionRepository.getIssuers(offset, limit)
    }
}