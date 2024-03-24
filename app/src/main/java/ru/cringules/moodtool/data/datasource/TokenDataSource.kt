package ru.cringules.moodtool.data.datasource

import kotlinx.coroutines.flow.first
import ru.cringules.moodtool.data.datastore.AuthDataStore
import javax.inject.Inject

class TokenDataSource @Inject constructor(
    private val authDataStore: AuthDataStore
) {
    suspend fun getToken(): String? {
        return authDataStore.getToken.first()
    }

    suspend fun setToken(token: String?) {
        authDataStore.saveToken(token)
    }
}