package ru.cringules.moodtool.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import ru.cringules.moodtool.model.Mood

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class MoodEntry(
    val id: String? = null,
    val mood: Mood = Mood(),
    @EncodeDefault
    val timestamp: Instant = Clock.System.now(),
    @EncodeDefault
    val tags: List<String> = listOf()
)
