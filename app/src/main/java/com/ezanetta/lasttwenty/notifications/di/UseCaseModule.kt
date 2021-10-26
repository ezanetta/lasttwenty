package com.ezanetta.lasttwenty.notifications.di

import com.ezanetta.lasttwenty.notifications.data.repository.NotificationsRepository
import com.ezanetta.lasttwenty.notifications.domain.usecase.GetDatabaseNotificationsUseCase
import com.ezanetta.lasttwenty.notifications.domain.usecase.GetNotificationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetDatabaseNotificationsUseCase(
        notificationRepository: NotificationsRepository
    ): GetNotificationsUseCase {
        return GetDatabaseNotificationsUseCase(notificationRepository)
    }
}