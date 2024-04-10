package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.MoodEntry
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.MyRecordRepository
import javax.inject.Inject

class EditEntryUseCase @Inject constructor(
    private val myRecordRepository: MyRecordRepository
) {
    suspend operator fun invoke(id: Int, moodEntry: MoodEntry): RepositoryResponse<Unit> {
        return myRecordRepository.updateRecord(id, moodEntry)
    }
}