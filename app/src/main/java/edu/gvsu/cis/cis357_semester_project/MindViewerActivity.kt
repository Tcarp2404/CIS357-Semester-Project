package edu.gvsu.cis.cis357_semester_project

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MindViewerActivity : AppCompatActivity() {

    private var index = 0
    private lateinit var list: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mind_viewer)

        val txtThought = findViewById<TextView>(R.id.txtThought)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnPrev = findViewById<Button>(R.id.btnPrev)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val layout = findViewById<LinearLayout>(R.id.layout)

        val emotionName = intent.getStringExtra("emotion")!!
        val emotion = Emotion.valueOf(emotionName)

        list = FakeData.thoughtsByEmotion[emotion] ?: mutableListOf()

        when (emotion) {
            Emotion.HAPPY -> layout.setBackgroundColor(Color.YELLOW)
            Emotion.SAD -> layout.setBackgroundColor(Color.BLUE)
            Emotion.MOTIVATED -> layout.setBackgroundColor(Color.RED)
            Emotion.CALM -> layout.setBackgroundColor(Color.GREEN)
        }

        fun showQuote() {
            if (list.isNotEmpty()) {
                txtThought.text = list[index]
            } else {
                txtThought.text = "No thoughts available"
            }
        }

        showQuote()

        btnNext.setOnClickListener {
            if (list.isNotEmpty()) {
                index = (index + 1) % list.size
                showQuote()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnPrev.setOnClickListener {
            if (list.isNotEmpty()) {
                index = if (index - 1 < 0) list.size - 1 else index - 1
                showQuote()
            }
        }
    }
}