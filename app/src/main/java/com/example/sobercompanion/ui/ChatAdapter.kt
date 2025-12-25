package com.example.sobercompanion.ui

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sobercompanion.R
import com.example.sobercompanion.model.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textMessageBody: TextView = view.findViewById(R.id.textMessageBody)
        val container: LinearLayout = view as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.textMessageBody.text = message.content

        if (message.isUser) {
            holder.container.gravity = Gravity.END
            holder.textMessageBody.setBackgroundColor(0xFFBB86FC.toInt()) // Purple 200
            holder.textMessageBody.setTextColor(0xFF000000.toInt())
        } else {
            holder.container.gravity = Gravity.START
            holder.textMessageBody.setBackgroundColor(0xFFE0E0E0.toInt())
            holder.textMessageBody.setTextColor(0xFF000000.toInt())
        }
    }

    override fun getItemCount() = messages.size
}
