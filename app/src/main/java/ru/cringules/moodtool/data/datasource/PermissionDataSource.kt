package ru.cringules.moodtool.data.datasource

import retrofit2.Response
import ru.cringules.moodtool.data.api.PermissionApi
import ru.cringules.moodtool.data.model.PermissionUserRequest
import javax.inject.Inject

class PermissionDataSource @Inject constructor(
    private val permissionApi: PermissionApi
) {
    suspend fun issuePermission(request: PermissionUserRequest, token: String): Response<Unit> {
        return permissionApi.issuePermission(request, "Bearer $token")
    }

    suspend fun recallPermission(request: PermissionUserRequest, token: String): Response<Unit> {
        return permissionApi.recallPermission(request, "Bearer $token")
    }

    suspend fun getIssuers(token: String, offset: Int = 0, limit: Int = 100): Response<List<String>> {
        return permissionApi.getIssuers("Bearer $token", offset, limit)
    }

    suspend fun getRecipients(token: String, offset: Int = 0, limit: Int = 100): Response<List<String>> {
        return permissionApi.getRecipients("Bearer $token", offset, limit)
    }
}