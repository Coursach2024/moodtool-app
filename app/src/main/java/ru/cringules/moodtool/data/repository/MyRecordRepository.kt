package ru.cringules.moodtool.data.repository

import ru.cringules.moodtool.data.datasource.MyRecordDataSource
import ru.cringules.moodtool.data.datasource.TokenDataSource
import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import javax.inject.Inject

class MyRecordRepository @Inject constructor(
    private val myRecordDataSource: MyRecordDataSource,
    private val tokenDataSource: TokenDataSource
) {
    suspend fun createMoodEntry(moodEntry: MoodEntry): RepositoryResponse<Unit> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.createMoodEntry(moodEntry, token)
        }
    }

    suspend fun getRecords(offset: Int = 0, limit: Int = 100): RepositoryResponse<List<MoodEntry>> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.getRecords(token, offset, limit)
        }
    }

    suspend fun getRecord(id: Int): RepositoryResponse<MoodEntry> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.getRecord(id, token)
        }
    }

    suspend fun updateRecord(id: Int, moodEntry: MoodEntry): RepositoryResponse<Unit> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.updateRecord(id, moodEntry, token)
        }
    }

    suspend fun deleteRecord(id: Int): RepositoryResponse<Unit> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            myRecordDataSource.deleteRecord(id, token)
        }
    }
}
