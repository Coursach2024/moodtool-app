package ru.cringules.moodtool

import ru.cringules.moodtool.model.MoodEntry

sealed interface MoodEntriesState {
    object Loading : MoodEntriesState
    class Success(val moodEntries: List<MoodEntry>) : MoodEntriesState
    object Error : MoodEntriesState
}