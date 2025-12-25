package com.example.sobercompanion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sobercompanion.ai.BehavioralScienceCoach
import com.example.sobercompanion.ai.SoberCoach
import com.example.sobercompanion.model.ChatMessage
import com.example.sobercompanion.ui.ChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var daysSober = 0
    private lateinit var textViewDaysSober: TextView
    private lateinit var buttonCheckIn: Button
    private lateinit var sharedPreferences: SharedPreferences

    // Chat components
    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    // AI Coach
    private val soberCoach: SoberCoach = BehavioralScienceCoach()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Views
        textViewDaysSober = findViewById(R.id.textViewDaysSober)
        buttonCheckIn = findViewById(R.id.buttonCheckIn)
        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        // Init Persistence
        sharedPreferences = getSharedPreferences("SoberCompanionPrefs", Context.MODE_PRIVATE)
        daysSober = sharedPreferences.getInt("daysSober", 0)
        updateSoberStatsUI()

        // Init Chat
        chatAdapter = ChatAdapter(chatMessages)
        recyclerViewChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerViewChat.adapter = chatAdapter

        // Add initial greeting
        addMessage(ChatMessage("Hello! I'm your Sober Companion. I'm powered by behavioral science principles to help you navigate cravings, stress, and celebrations. How are you feeling right now?", false))

        // Check In Logic
        buttonCheckIn.setOnClickListener {
            daysSober++
            saveProgress()
            updateSoberStatsUI()
            addMessage(ChatMessage("I just checked in for another day!", true))
            respondToUser("I just checked in for another day!")
        }

        buttonCheckIn.setOnLongClickListener {
             daysSober = 0
             saveProgress()
             updateSoberStatsUI()
             addMessage(ChatMessage("I relapsed and reset my counter.", true))
             respondToUser("I relapsed and reset my counter.")
             true
        }

        // Send Logic
        buttonSend.setOnClickListener {
            val text = editTextMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                editTextMessage.text.clear()
                addMessage(ChatMessage(text, true))
                respondToUser(text)
            }
        }
    }

    private fun respondToUser(userText: String) {
        // Use Coroutines to call the AI asynchronously
        CoroutineScope(Dispatchers.Main).launch {
            val response = soberCoach.getResponse(userText, chatMessages)
            addMessage(ChatMessage(response, false))
        }
    }

    private fun addMessage(message: ChatMessage) {
        chatMessages.add(message)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        recyclerViewChat.scrollToPosition(chatMessages.size - 1)
    }

    private fun saveProgress() {
        val editor = sharedPreferences.edit()
        editor.putInt("daysSober", daysSober)
        editor.apply()
    }

    private fun updateSoberStatsUI() {
        textViewDaysSober.text = "$daysSober Days Sober"
    }
}
