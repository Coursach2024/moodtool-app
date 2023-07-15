package ru.cringules.moodtool

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class MoodEntry(
    val id: String? = null,
    @EncodeDefault
    val angryAfraid: Int = 0,
    @EncodeDefault
    val cheerfulDepressed: Int = 0,
    @EncodeDefault
    val willfulYielding: Int = 0,
    @EncodeDefault
    val pressuredLonely: Int = 0,
    @EncodeDefault
    val timestamp: Instant = Clock.System.now(),
    @EncodeDefault
    val tags: List<String> = listOf()
)
