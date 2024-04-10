package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.Mood
import ru.cringules.moodtool.data.model.MoodConditions
import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.AnalyticsRepository
import javax.inject.Inject

class GetPredictionUseCase @Inject constructor(
    private val analyticsRepository: AnalyticsRepository
) {
    suspend operator fun invoke(moodConditions: MoodConditions): RepositoryResponse<Mood> {
        return analyticsRepository.predictMood(moodConditions)
    }
}