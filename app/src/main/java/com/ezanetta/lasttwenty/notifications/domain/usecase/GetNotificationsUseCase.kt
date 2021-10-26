package com.ezanetta.lasttwenty.notifications.domain.usecase

import com.ezanetta.lasttwenty.notifications.data.db.Notification
import kotlinx.coroutines.flow.Flow

interface GetNotificationsUseCase {
    suspend fun getNotifications(): Flow<List<Notification>>
}