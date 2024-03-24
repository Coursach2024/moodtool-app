package ru.cringules.moodtool.data.datasource

import retrofit2.Response
import ru.cringules.moodtool.data.api.OthersRecordApi
import ru.cringules.moodtool.data.model.MoodEntry
import javax.inject.Inject

class OthersRecordDataSource @Inject constructor(
    private val othersRecordApi: OthersRecordApi
){
    suspend fun getRecords(username: String, token: String, offset: Int = 0, limit: Int = 100): Response<List<MoodEntry>> {
        return othersRecordApi.getRecords(username, "Bearer $token", offset, limit)
    }
}