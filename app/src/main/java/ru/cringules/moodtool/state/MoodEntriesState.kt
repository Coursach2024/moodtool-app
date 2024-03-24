package ru.cringules.moodtool.state

import ru.cringules.moodtool.data.model.MoodEntry

sealed interface MoodEntriesState {
    object Loading : MoodEntriesState
    class Success(val moodEntries: List<MoodEntry>) : MoodEntriesState
    object Error : MoodEntriesState
}