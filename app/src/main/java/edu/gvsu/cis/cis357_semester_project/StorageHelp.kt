package edu.gvsu.cis.cis357_semester_project

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object StorageHelper {

    private const val FILE_NAME = "thoughts.json"

    fun save(context: Context, data: Map<Emotion, MutableList<Thought>>) {
        val json = Gson().toJson(data)
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(json)
    }

    fun load(context: Context): MutableMap<Emotion, MutableList<Thought>> {
        val file = File(context.filesDir, FILE_NAME)

        if (!file.exists()) return FakeData.thoughtsByEmotion

        val json = file.readText()

        val type = object : TypeToken<MutableMap<Emotion, MutableList<Thought>>>() {}.type

        return Gson().fromJson(json, type)
    }
}