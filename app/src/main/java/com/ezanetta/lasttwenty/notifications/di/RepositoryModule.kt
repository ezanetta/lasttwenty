package com.ezanetta.lasttwenty.notifications.di

import com.ezanetta.lasttwenty.notifications.data.db.NotificationDao
import com.ezanetta.lasttwenty.notifications.data.repository.DatabaseNotificationRepository
import com.ezanetta.lasttwenty.notifications.data.repository.NotificationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideNotificationsRepository(
        notificationDao: NotificationDao
    ): NotificationsRepository {
        return DatabaseNotificationRepository(notificationDao)
    }
}