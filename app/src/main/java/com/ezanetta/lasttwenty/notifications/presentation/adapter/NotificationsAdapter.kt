package com.ezanetta.lasttwenty.notifications.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezanetta.lasttwenty.databinding.NotificationItemLayoutBinding
import com.ezanetta.lasttwenty.notifications.presentation.adapter.viewholder.NotificationViewHolder
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationItem

class NotificationsAdapter :
    RecyclerView.Adapter<NotificationViewHolder>() {

    private var notifications: MutableList<NotificationItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = NotificationItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindNotification(notifications[position])
    }

    override fun getItemCount() = notifications.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(newNotifications: List<NotificationItem>) {
        notifications.clear()
        notifications.addAll(newNotifications)
        notifyDataSetChanged()
    }
}