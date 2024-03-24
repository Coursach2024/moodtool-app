package ru.cringules.moodtool.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(@SerialName("record_count") val recordCount: Int)
