package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.OthersRecordRepository
import javax.inject.Inject

class ListUserEntriesUseCase @Inject constructor(
    private val othersRecordRepository: OthersRecordRepository
) {
    suspend operator fun invoke(username: String, offset: Int = 0, limit: Int = 100): RepositoryResponse<List<MoodEntry>> {
        return othersRecordRepository.getRecords(username, offset, limit)
    }
}