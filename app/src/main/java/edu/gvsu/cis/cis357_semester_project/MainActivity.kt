package edu.gvsu.cis.cis357_semester_project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.emotionSpinner)
        val btnGetQuote = findViewById<Button>(R.id.btnGetQuote)
        val btnManage = findViewById<Button>(R.id.btnManage)

        val emotions = Emotion.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emotions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        btnGetQuote.setOnClickListener {
            val selectedEmotion = spinner.selectedItem.toString()

            val intent = Intent(this, MindViewerActivity::class.java)
            intent.putExtra("emotion", selectedEmotion)
            startActivity(intent)
        }

        btnManage.setOnClickListener {
            val intent = Intent(this, ManageMindActivity::class.java)
            startActivity(intent)
        }
    }
}