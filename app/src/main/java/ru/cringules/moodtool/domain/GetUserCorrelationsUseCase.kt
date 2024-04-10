package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCorrelations
import ru.cringules.moodtool.data.repository.AnalyticsRepository
import javax.inject.Inject

class GetUserCorrelationsUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    suspend operator fun invoke(username: String): RepositoryResponse<UserCorrelations> {
        return analyticsRepository.getCorrelation(username)
    }
}