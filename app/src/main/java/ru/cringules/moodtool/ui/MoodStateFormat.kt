package ru.cringules.moodtool.ui

import ru.cringules.moodtool.model.MoodEntry

private fun formatScale(value: Int, positiveLabel: String, negativeLabel: String): String {
    if (value < 0) {
        return "$negativeLabel ${-value}"
    }

    return "$positiveLabel $value"
}

fun MoodEntry.formatAngryAfraid(): String {
    return formatScale(mood.angryAfraid, "Angry", "Afraid")
}

fun MoodEntry.formatCheerfulDepressed(): String {
    return formatScale(mood.cheerfulDepressed, "Cheerful", "Depressed")
}

fun MoodEntry.formatWillfulYielding(): String {
    return formatScale(mood.willfulYielding, "Willful", "Yielding")
}

fun MoodEntry.formatPressuredLonely(): String {
    return formatScale(mood.pressuredLonely, "Pressured", "Lonely")
}