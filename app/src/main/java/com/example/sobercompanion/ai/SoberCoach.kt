package com.example.sobercompanion.ai

import com.example.sobercompanion.model.ChatMessage

interface SoberCoach {
    suspend fun getResponse(userMessage: String, history: List<ChatMessage>): String
}
