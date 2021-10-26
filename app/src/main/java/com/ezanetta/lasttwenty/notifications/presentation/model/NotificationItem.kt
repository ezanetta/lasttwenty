package com.ezanetta.lasttwenty.notifications.presentation.model


data class NotificationItem(
    val title: String,
    val text: String,
    val packageName: String,
    val active: Boolean
)