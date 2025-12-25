package com.example.sobercompanion.ai

import com.example.sobercompanion.model.ChatMessage
import java.util.*

/**
 * A simulation of a Behavioral Science based AI Coach.
 * In a production environment with API keys, this would be replaced or augmented by an LLM call.
 * This class uses heuristic matching to provide CBT/DBT informed responses.
 */
class BehavioralScienceCoach : SoberCoach {

    override suspend fun getResponse(userMessage: String, history: List<ChatMessage>): String {
        // Simulate network delay for realism
        kotlinx.coroutines.delay(1000)

        val lowerMsg = userMessage.lowercase(Locale.getDefault())

        return when {
            // Urge/Craving -> Surf the Urge / Play the Tape Forward
            lowerMsg.contains("want") && (lowerMsg.contains("drink") || lowerMsg.contains("alcohol")) ->
                "I hear that you're feeling an urge. That's a normal part of the process. Let's 'play the tape forward'. If you drink now, how will you feel in 10 minutes? How about tomorrow morning?"

            lowerMsg.contains("crav") || lowerMsg.contains("urge") ->
                "Cravings are like waves; they peak and then subside. This is called 'surfing the urge'. Can you distract yourself for just 15 minutes? What's a healthy activity you can do right now?"

            // Relapse -> Compassion / Analysis
            lowerMsg.contains("relapse") || lowerMsg.contains("slipped") || lowerMsg.contains("drank") ->
                "Thank you for being honest. Relapse can be a stepping stone if we learn from it. Let's not beat ourselves up. What was the trigger that led to this? We can plan for it next time."

            // Stress/Anxiety -> Grounding
            lowerMsg.contains("stress") || lowerMsg.contains("anxious") || lowerMsg.contains("worried") ->
                "It sounds like you're carrying a heavy load. Stress is a major trigger. Let's try a grounding exercise: Name 5 things you can see, 4 you can touch, 3 you can hear, 2 you can smell, and 1 you can taste."

            // Sadness/Loneliness -> Connection/Values
            lowerMsg.contains("sad") || lowerMsg.contains("lonely") || lowerMsg.contains("depressed") ->
                "I'm sorry you're feeling this way. You are not alone in this journey. Remember why you started. What is one core value (like family, health, freedom) that sobriety helps you protect?"

            // Boredom -> Action
            lowerMsg.contains("bored") ->
                "Boredom is dangerous. Sobriety opens up time that used to be filled with drinking. What's a hobby you've neglected or a new skill you'd like to learn?"

            // Celebration
            lowerMsg.contains("happy") || lowerMsg.contains("good") || lowerMsg.contains("great") || lowerMsg.contains("proud") ->
                "That's wonderful! It's important to celebrate these wins. Hold onto this feeling. What did you do differently today that helped?"

            // Check-in
            lowerMsg.contains("hello") || lowerMsg.contains("hi") ->
                "Hello! I'm here to support you. How are you feeling today?"

            // Default
            else -> "I'm listening. Tell me more about what's going on in your mind right now. I'm here to help you process it."
        }
    }
}
