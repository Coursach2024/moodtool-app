package ru.cringules.moodtool.data.repository

import ru.cringules.moodtool.data.datasource.PermissionDataSource
import ru.cringules.moodtool.data.datasource.TokenDataSource
import ru.cringules.moodtool.data.model.PermissionUserRequest
import ru.cringules.moodtool.data.model.RepositoryResponse
import javax.inject.Inject

class PermissionRepository @Inject constructor(
    private val permissionDataSource: PermissionDataSource,
    private val tokenDataSource: TokenDataSource
) {
    suspend fun issuePermission(request: PermissionUserRequest): RepositoryResponse<Unit> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            permissionDataSource.issuePermission(request, token)
        }
    }

    suspend fun recallPermission(request: PermissionUserRequest): RepositoryResponse<Unit> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            permissionDataSource.recallPermission(request, token)
        }
    }

    suspend fun getIssuers(offset: Int = 0, limit: Int = 100): RepositoryResponse<List<String>> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            permissionDataSource.getIssuers(token, offset, limit)
        }
    }

    suspend fun getRecipients(offset: Int = 0, limit: Int = 100): RepositoryResponse<List<String>> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            permissionDataSource.getRecipients(token, offset, limit)
        }
    }
}