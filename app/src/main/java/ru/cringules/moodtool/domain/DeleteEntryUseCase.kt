package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.MyRecordRepository
import javax.inject.Inject

class DeleteEntryUseCase @Inject constructor(
    private val myRecordRepository: MyRecordRepository
) {
    suspend operator fun invoke(id: Int): RepositoryResponse<Unit> {
        return myRecordRepository.deleteRecord(id)
    }
}