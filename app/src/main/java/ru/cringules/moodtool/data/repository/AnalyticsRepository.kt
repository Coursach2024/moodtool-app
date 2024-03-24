package ru.cringules.moodtool.data.repository

import ru.cringules.moodtool.data.datasource.AnalyticsDataSource
import ru.cringules.moodtool.data.datasource.TokenDataSource
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodConditions
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCorrelations
import javax.inject.Inject

class AnalyticsRepository @Inject constructor(
    private val analyticsDataSource: AnalyticsDataSource,
    private val tokenDataSource: TokenDataSource
) {
    suspend fun predictMood(moodConditions: MoodConditions): RepositoryResponse<Mood> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            analyticsDataSource.predictMood(moodConditions, token)
        }
    }

    suspend fun getDependencies(): RepositoryResponse<Map<String, Correlations>> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            analyticsDataSource.getDependencies(token)
        }
    }

    suspend fun getCorrelation(username: String): RepositoryResponse<UserCorrelations> {
        val token = tokenDataSource.getToken() ?: return RepositoryResponse.AuthError()

        return RepositoryResponse.fromCall {
            analyticsDataSource.getCorrelation(username, token)
        }
    }
}