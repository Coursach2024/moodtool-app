package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.MyRecordRepository
import javax.inject.Inject

class CreateEntryUseCase @Inject constructor(
    private val myRecordRepository: MyRecordRepository
) {
    suspend operator fun invoke(moodEntry: MoodEntry): RepositoryResponse<Unit> {
        return myRecordRepository.createMoodEntry(moodEntry)
    }
}