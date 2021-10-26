package com.ezanetta.lasttwenty.notifications.domain.usecase

import com.ezanetta.lasttwenty.notifications.data.db.Notification
import com.ezanetta.lasttwenty.notifications.data.repository.NotificationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDatabaseNotificationsUseCase @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : GetNotificationsUseCase {

    override suspend fun getNotifications(): Flow<List<Notification>> {
        return notificationsRepository.getAllNotifications()
    }
}