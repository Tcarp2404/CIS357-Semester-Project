package edu.gvsu.cis.cis357_semester_project

import androidx.compose.ui.graphics.Color

enum class Emotion {
    HAPPY, SAD, MOTIVATED, CALM
}

data class Thought(
    val text: String,
    val imagePath: String? = null
)

object FakeData {

    val thoughtsByEmotion = mutableMapOf(
        Emotion.HAPPY to mutableListOf(
            Thought("Happiness looks good on you 😊")
        ),
        Emotion.SAD to mutableListOf(
            Thought("This feeling will pass")
        ),
        Emotion.MOTIVATED to mutableListOf(
            Thought("Keep pushing forward!")
        ),
        Emotion.CALM to mutableListOf(
            Thought("Breathe in, breathe out")
        )
    )
}

fun getEmotionColor(emotion: Emotion): Color {
    return when (emotion) {
        Emotion.HAPPY -> Color(0xFFFFF176)
        Emotion.SAD -> Color(0xFF64B5F6)
        Emotion.CALM -> Color(0xFFB39DDB)
        Emotion.MOTIVATED -> Color(0xFFFFA726)
    }
}