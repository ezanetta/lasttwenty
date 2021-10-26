package com.ezanetta.lasttwenty.notifications.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification ORDER BY notification.created_at DESC LIMIT 20")
    fun getAll(): Flow<List<Notification>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<Notification>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notifications: Notification)
}