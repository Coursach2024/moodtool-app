package ru.cringules.moodtool.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.cringules.moodtool.data.dto.UnixTimestampSerializer
import ru.cringules.moodtool.data.model.Mood

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class MoodEntry(
    val id: Int? = null,
    @SerialName("user_id")
    val userId: Int? = null,
    val mood: Mood = Mood(),
    @EncodeDefault
    @Serializable(with = UnixTimestampSerializer::class)
    val timestamp: Instant = Clock.System.now(),
    @EncodeDefault
    val tags: List<String> = listOf()
)
