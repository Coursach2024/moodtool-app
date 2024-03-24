package ru.cringules.moodtool.data.datasource

import retrofit2.Response
import ru.cringules.moodtool.data.api.AnalyticsApi
import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodConditions
import ru.cringules.moodtool.data.model.UserCorrelations
import javax.inject.Inject

class AnalyticsDataSource @Inject constructor(
    private val analyticsApi: AnalyticsApi
) {
    suspend fun predictMood(moodConditions: MoodConditions, token: String): Response<Mood> {
        return analyticsApi.predictMood(moodConditions, "Bearer $token")
    }

    suspend fun getDependencies(token: String): Response<Map<String, Correlations>> {
        return analyticsApi.getDependencies("Bearer $token")
    }

    suspend fun getCorrelation(username: String, token: String): Response<UserCorrelations> {
        return analyticsApi.getCorrelation(username, "Bearer $token")
    }
}