package ru.cringules.moodtool.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCorrelations(
    @SerialName("angry_afraid")
    val angryAfraid: Correlations,
    @SerialName("cheerful_depressed")
    val cheerfulDepressed: Correlations,
    @SerialName("willful_yielding")
    val willfulYielding: Correlations,
    @SerialName("pressured_lonely")
    val pressuredLonely: Correlations
)
