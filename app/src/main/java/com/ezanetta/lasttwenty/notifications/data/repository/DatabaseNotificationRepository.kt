package com.ezanetta.lasttwenty.notifications.data.repository

import com.ezanetta.lasttwenty.notifications.data.db.Notification
import com.ezanetta.lasttwenty.notifications.data.db.NotificationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseNotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationsRepository {

    override suspend fun getAllNotifications(): Flow<List<Notification>> {
        return notificationDao.getAll()
    }

    override suspend fun insertAll(notifications: List<Notification>) {
        notificationDao.insertAll(notifications)
    }

    override suspend fun insert(notification: Notification) {
        notificationDao.insert(notification)
    }
}