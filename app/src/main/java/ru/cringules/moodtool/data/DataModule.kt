package ru.cringules.moodtool.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cringules.moodtool.MoodApiService

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun moodEntriesRepository(authDataStore: AuthDataStore, moodApiService: MoodApiService): MoodEntriesRepository {
        return MoodEntriesApiRepository(authDataStore, moodApiService)
    }
}