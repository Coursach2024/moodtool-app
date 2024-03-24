package ru.cringules.moodtool.data.repository

import ru.cringules.moodtool.data.datasource.OthersRecordDataSource
import ru.cringules.moodtool.data.datasource.TokenDataSource
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import javax.inject.Inject

class OthersRecordRepository @Inject constructor(
    private val othersRecordDataSource: OthersRecordDataSource,
    private val tokenDataSource: TokenDataSource
) {
    suspend fun getRecords(
        username: String,
        offset: Int = 0,
        limit: Int = 100
    ): RepositoryResponse<List<MoodEntry>> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            othersRecordDataSource.getRecords(username, token, offset, limit)
        }
    }
}