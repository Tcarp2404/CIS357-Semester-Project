package edu.gvsu.cis.cis357_semester_project

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ManageMindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_mind)

        val input = findViewById<EditText>(R.id.inputThought)
        val spinner = findViewById<Spinner>(R.id.emotionSpinner)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val emotions = Emotion.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emotions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val text = input.text.toString()
            val selectedEmotion = Emotion.valueOf(spinner.selectedItem.toString())

            if (text.isNotEmpty()) {
                FakeData.thoughtsByEmotion[selectedEmotion]?.add(text)
                Toast.makeText(this, "Thought Saved!", Toast.LENGTH_SHORT).show()
                input.text.clear()
            } else {
                Toast.makeText(this, "Please enter a thought", Toast.LENGTH_SHORT).show()
            }
        }
    }
}