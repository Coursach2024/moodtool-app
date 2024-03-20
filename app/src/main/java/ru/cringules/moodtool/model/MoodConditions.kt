package ru.cringules.moodtool.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class MoodConditions(
    @EncodeDefault
    val timestamp: Instant = Clock.System.now(),
    @EncodeDefault
    val tags: List<String> = listOf()
)
