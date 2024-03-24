package ru.cringules.moodtool.data.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Mood(
    @EncodeDefault
    @SerialName("angry_afraid")
    val angryAfraid: Int = 0,
    @EncodeDefault
    @SerialName("cheerful_depressed")
    val cheerfulDepressed: Int = 0,
    @EncodeDefault
    @SerialName("willful_yielding")
    val willfulYielding: Int = 0,
    @EncodeDefault
    @SerialName("pressured_lonely")
    val pressuredLonely: Int = 0,
)
