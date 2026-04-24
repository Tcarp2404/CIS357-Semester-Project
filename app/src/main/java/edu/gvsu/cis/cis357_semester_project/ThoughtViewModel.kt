package edu.gvsu.cis.cis357_semester_project

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThoughtViewModel : ViewModel() {

    private val _thoughts = MutableStateFlow(mutableMapOf<Emotion, MutableList<Thought>>())
    val thoughts: StateFlow<Map<Emotion, MutableList<Thought>>> = _thoughts

    private val _selectedEmotion = MutableStateFlow(Emotion.HAPPY)
    val selectedEmotion: StateFlow<Emotion> = _selectedEmotion

    fun loadData(context: Context) {
        _thoughts.value = StorageHelper.load(context)
    }

    fun setEmotion(emotion: Emotion) {
        _selectedEmotion.value = emotion
    }

    fun addThought(
        text: String,
        emotion: Emotion,
        context: Context,
        imagePath: String? = null
    ) {
        val currentMap = _thoughts.value.toMutableMap()
        val currentList = currentMap[emotion]?.toMutableList() ?: mutableListOf()

        currentList.add(Thought(text, imagePath))

        currentMap[emotion] = currentList
        _thoughts.value = currentMap

        StorageHelper.save(context, _thoughts.value)
    }

    fun removeThought(
        emotion: Emotion,
        index: Int,
        context: Context
    ) {
        val currentMap = _thoughts.value.toMutableMap()
        val currentList = currentMap[emotion]?.toMutableList() ?: return

        if (index in currentList.indices) {
            currentList.removeAt(index)
        }

        currentMap[emotion] = currentList
        _thoughts.value = currentMap

        StorageHelper.save(context, _thoughts.value)
    }

    fun getThoughtsForEmotion(): List<Thought> {
        return _thoughts.value[_selectedEmotion.value] ?: emptyList()
    }
}