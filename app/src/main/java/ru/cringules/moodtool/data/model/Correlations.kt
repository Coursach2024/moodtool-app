package ru.cringules.moodtool.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Correlations(
    @SerialName("angry_afraid")
    val angryAfraid: Double,
    @SerialName("cheerful_depressed")
    val cheerfulDepressed: Double,
    @SerialName("willful_yielding")
    val willfulYielding: Double,
    @SerialName("pressured_lonely")
    val pressuredLonely: Double
)
