package ru.cringules.moodtool.domain

import ru.cringules.moodtool.data.model.RepositoryResponse
import ru.cringules.moodtool.data.model.UserCredentials
import ru.cringules.moodtool.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(userCredentials: UserCredentials): RepositoryResponse<Any> {
        return authRepository.register(userCredentials)
    }
}