package com.ezanetta.lasttwenty.notifications.extensions

import android.service.notification.StatusBarNotification
import android.view.View
import com.ezanetta.lasttwenty.notifications.data.db.Notification

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun StatusBarNotification?.toNotification(active: Boolean): Notification? {
    return this?.let {
        val extras = it.notification?.extras
        val id = if (it.id > 0) it.id else (it.postTime.toString()).hashCode()
        val title = extras?.get("android.title")
        val text = extras?.get("android.text")
        val packageName = it.packageName
        val createdAt = it.notification.`when`

        if (title != null && text != null) {
            Notification(
                id,
                title.toString(),
                text.toString(),
                packageName,
                createdAt,
                active
            )
        } else {
            null
        }
    }
}