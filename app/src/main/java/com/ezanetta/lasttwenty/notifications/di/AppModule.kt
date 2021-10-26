package com.ezanetta.lasttwenty.notifications.di

import android.content.Context
import androidx.room.Room
import com.ezanetta.lasttwenty.notifications.data.db.AppDatabase
import com.ezanetta.lasttwenty.notifications.data.db.NotificationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "notifications.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNotificationDao(db: AppDatabase): NotificationDao {
        return db.notificationDao()
    }
}