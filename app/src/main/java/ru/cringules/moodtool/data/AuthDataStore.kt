package ru.cringules.moodtool.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = "login")

        private val usernameKey = stringPreferencesKey("username")
        private val tokenKey = stringPreferencesKey("token")
    }

    val getToken: Flow<String?> = context.loginDataStore.data.map { preferences: Preferences ->
        preferences[tokenKey]
    }

    suspend fun saveToken(token: String) {
        context.loginDataStore.edit { preferences: MutablePreferences ->
            preferences[tokenKey] = token
        }
    }
}