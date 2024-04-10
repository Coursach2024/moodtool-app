package ru.cringules.moodtool.ui

import ru.cringules.moodtool.data.model.Mood

private fun formatScale(value: Int, negativeLabel: String, positiveLabel: String): String {
    if (value < 0) {
        return "$negativeLabel ${-value}"
    }

    return "$positiveLabel $value"
}

fun Mood.formatAngryAfraid(): String {
    return formatScale(angryAfraid, "Angry", "Afraid")
}

fun Mood.formatCheerfulDepressed(): String {
    return formatScale(cheerfulDepressed, "Cheerful", "Depressed")
}

fun Mood.formatWillfulYielding(): String {
    return formatScale(willfulYielding, "Willful", "Yielding")
}

fun Mood.formatPressuredLonely(): String {
    return formatScale(pressuredLonely, "Pressured", "Lonely")
}