package ru.cringules.moodtool.data.datasource

import retrofit2.Response
import ru.cringules.moodtool.data.api.MyRecordApi
import ru.cringules.moodtool.data.model.Info
import ru.cringules.moodtool.data.model.MoodEntry
import javax.inject.Inject

class MyRecordDataSource @Inject constructor(
    private val myRecordApi: MyRecordApi
) {
    suspend fun createMoodEntry(moodEntry: MoodEntry, token: String): Response<Unit> {
        return myRecordApi.createMoodEntry(moodEntry, "Bearer $token")
    }

    suspend fun getRecords(token: String, offset: Int = 0, limit: Int = 100): Response<List<MoodEntry>> {
        return myRecordApi.getRecords("Bearer $token", offset, limit)
    }

    suspend fun getRecord(id: Int, token: String): Response<MoodEntry> {
        return myRecordApi.getRecord(id, "Bearer $token")
    }

    suspend fun updateRecord(id: Int, moodEntry: MoodEntry, token: String): Response<Unit> {
        return myRecordApi.updateRecord(id, moodEntry, "Bearer $token")
    }

    suspend fun deleteRecord(id: Int, token: String): Response<Unit> {
        return myRecordApi.deleteRecord(id, "Bearer $token")
    }

    suspend fun getInfo(token: String): Response<Info> {
        return myRecordApi.getInfo("Bearer $token")
    }
}