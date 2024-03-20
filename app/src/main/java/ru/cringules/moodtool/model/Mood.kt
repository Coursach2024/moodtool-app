package ru.cringules.moodtool.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Mood(
    @EncodeDefault
    val angryAfraid: Int = 0,
    @EncodeDefault
    val cheerfulDepressed: Int = 0,
    @EncodeDefault
    val willfulYielding: Int = 0,
    @EncodeDefault
    val pressuredLonely: Int = 0,
)
