package com.ezanetta.lasttwenty.notifications.data.repository

import com.ezanetta.lasttwenty.notifications.data.db.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationsRepository {
    suspend fun getAllNotifications(): Flow<List<Notification>>
    suspend fun insertAll(notifications: List<Notification>)
    suspend fun insert(notification: Notification)
}