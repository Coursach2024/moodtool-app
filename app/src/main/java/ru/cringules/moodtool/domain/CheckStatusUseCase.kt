package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.repository.AuthRepository
import javax.inject.Inject

class CheckStatusUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): RepositoryResponse<Any> {
        return authRepository.checkLogin()
    }
}