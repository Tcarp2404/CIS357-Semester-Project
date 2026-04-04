package edu.gvsu.cis.cis357_semester_project

enum class Emotion {
    HAPPY, SAD, MOTIVATED, CALM
}

object FakeData {

    val thoughtsByEmotion = mutableMapOf(
        Emotion.HAPPY to mutableListOf(
            "Happiness looks good on you 😊",
            "Enjoy the little things"
        ),
        Emotion.SAD to mutableListOf(
            "This feeling will pass",
            "You are stronger than you think"
        ),
        Emotion.MOTIVATED to mutableListOf(
            "Keep pushing forward!",
            "You got this 💪"
        ),
        Emotion.CALM to mutableListOf(
            "Breathe in, breathe out",
            "Peace begins with you"
        )
    )
}