package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.Correlations
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.AnalyticsRepository
import javax.inject.Inject

class ListTagDependenciesUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    suspend operator fun invoke(): RepositoryResponse<Map<String, Correlations>> {
        return analyticsRepository.getDependencies()
    }
}