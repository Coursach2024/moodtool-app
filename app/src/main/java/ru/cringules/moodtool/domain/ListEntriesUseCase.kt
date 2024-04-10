package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.MyRecordRepository
import javax.inject.Inject

class ListEntriesUseCase @Inject constructor(
    private val myRecordRepository: MyRecordRepository
) {
    suspend operator fun invoke(offset: Int = 0, limit: Int = 100): RepositoryResponse<List<MoodEntry>> {
        return myRecordRepository.getRecords(offset, limit)
    }
}