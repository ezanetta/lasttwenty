package com.ezanetta.lasttwenty.notifications.data.services

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ezanetta.lasttwenty.notifications.data.db.Notification
import com.ezanetta.lasttwenty.notifications.data.repository.DatabaseNotificationRepository
import com.ezanetta.lasttwenty.notifications.extensions.toNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var databaseNotificationRepository: DatabaseNotificationRepository

    override fun onListenerConnected() {
        super.onListenerConnected()

        val notifications = mutableListOf<Notification>()

        activeNotifications.forEach {
            val notification = it.toNotification(true)
            notification?.let { thisNotification ->
                notifications.add(thisNotification)
            }
        }

        if (notifications.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                databaseNotificationRepository.insertAll(notifications)
            }
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn.toNotification(true)?.let {
            CoroutineScope(Dispatchers.IO).launch {
                databaseNotificationRepository.insert(it)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        sbn.toNotification(false)?.let {
            CoroutineScope(Dispatchers.IO).launch {
                databaseNotificationRepository.insert(it)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }
}