package ru.cringules.moodtool.data.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import ru.cringules.moodtool.data.dto.UnixTimestampSerializer

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class MoodConditions(
    @EncodeDefault
    @Serializable(with = UnixTimestampSerializer::class)
    val timestamp: Instant = Clock.System.now(),
    @EncodeDefault
    val tags: List<String> = listOf()
)
