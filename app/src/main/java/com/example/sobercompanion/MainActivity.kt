package com.example.sobercompanion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var daysSober = 0
    private lateinit var textViewDaysSober: TextView
    private lateinit var textViewAI: TextView
    private lateinit var buttonCheckIn: Button
    private lateinit var sharedPreferences: SharedPreferences

    private val encouragements = listOf(
        "You're doing great! Keep it up!",
        "One day at a time. You've got this.",
        "Sobriety is a journey, not a destination.",
        "Believe you can and you're halfway there.",
        "Stay strong, better days are coming.",
        "Your potential is endless. Go do what you were created to do.",
        "The best way out is always through."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewDaysSober = findViewById(R.id.textViewDaysSober)
        textViewAI = findViewById(R.id.textViewAI)
        buttonCheckIn = findViewById(R.id.buttonCheckIn)

        sharedPreferences = getSharedPreferences("SoberCompanionPrefs", Context.MODE_PRIVATE)
        daysSober = sharedPreferences.getInt("daysSober", 0)

        updateUI()

        buttonCheckIn.setOnClickListener {
            // In a real app, this would persist data and perhaps check dates.
            // For this demo, we'll just increment or reset for demonstration logic.
            // Let's implement a simple "Increment" for now as "Another day sober!"
            // To be more realistic for "Reset", we'd need a different flow.
            // Let's assume the button is "I stayed sober today" -> Increments count.
            daysSober++
            saveProgress()
            updateUI()
            giveEncouragement()
        }

        buttonCheckIn.setOnLongClickListener {
             // Long click to reset (Relapse scenario)
             daysSober = 0
             saveProgress()
             updateUI()
             textViewAI.text = "AI Companion: It's okay. We start again. Don't give up."
             true
        }
    }

    private fun saveProgress() {
        val editor = sharedPreferences.edit()
        editor.putInt("daysSober", daysSober)
        editor.apply()
    }

    private fun updateUI() {
        textViewDaysSober.text = "$daysSober Days Sober"
    }

    private fun giveEncouragement() {
        val random = Random()
        val message = encouragements[random.nextInt(encouragements.size)]
        textViewAI.text = "AI Companion: $message"
    }
}
