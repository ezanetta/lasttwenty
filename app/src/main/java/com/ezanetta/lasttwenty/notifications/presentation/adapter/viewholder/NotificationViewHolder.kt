package com.ezanetta.lasttwenty.notifications.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.view.View
import com.ezanetta.lasttwenty.databinding.NotificationItemLayoutBinding
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationItem

class NotificationViewHolder(private val binding: NotificationItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindNotification(notification: NotificationItem) {
        with(notification) {
            binding.title.text = title
            binding.description.text = text
            setAppIconImage(packageName)
        }
    }

    private fun setAppIconImage(packageName: String) {
        try {
            val icon: Drawable =
                binding.notificationImage.context.packageManager
                    .getApplicationIcon(packageName)
            binding.notificationImage.setImageDrawable(icon)
            binding.notificationImage.visibility = View.VISIBLE
        } catch (e: PackageManager.NameNotFoundException) {
            binding.notificationImage.visibility = View.GONE
        }
    }
}