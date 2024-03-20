package ru.cringules.moodtool.data

import kotlinx.coroutines.flow.first
import ru.cringules.moodtool.MoodApiService
import ru.cringules.moodtool.model.MoodConditions
import ru.cringules.moodtool.MoodEntriesState
import ru.cringules.moodtool.model.MoodEntry
import ru.cringules.moodtool.PredictedMoodState
import javax.inject.Inject

interface MoodEntriesRepository {
    suspend fun getMoodEntries(): MoodEntriesState
    suspend fun createMoodEntry(moodEntry: MoodEntry)
    suspend fun predictMood(moodConditions: MoodConditions): PredictedMoodState
}

class MoodEntriesApiRepository @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val moodApiService: MoodApiService
) : MoodEntriesRepository {

    override suspend fun getMoodEntries(): MoodEntriesState {
        val token = authDataStore.getToken.first() ?: return MoodEntriesState.Error
        return try {
            MoodEntriesState.Success(moodApiService.getMoodEntries("Bearer $token"))
        } catch (e: Exception) {
            MoodEntriesState.Error
        }
    }

    override suspend fun createMoodEntry(moodEntry: MoodEntry) {
        val token = authDataStore.getToken.first()
        moodApiService.createMoodEntry(moodEntry, "Bearer $token")
    }

    override suspend fun predictMood(moodConditions: MoodConditions): PredictedMoodState {
        val token = authDataStore.getToken.first() ?: return PredictedMoodState.Error
        return try {
            PredictedMoodState.Success(
                moodApiService.predictMood(
                    moodConditions,
                    "Bearer $token"
                )
            )
        } catch (e: Exception) {
            PredictedMoodState.Error
        }
    }
}
