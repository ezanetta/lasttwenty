package com.ezanetta.lasttwenty.notifications.presentation.model


sealed class NotificationsActivityState {
    class ShowNotifications(val notifications: ArrayList<NotificationItem>) :
        NotificationsActivityState()

    class ShowActiveNotifications(val notifications: ArrayList<NotificationItem>) :
        NotificationsActivityState()

    object ShowEmptyState : NotificationsActivityState()

    object ShowRequestNotificationPermissionState : NotificationsActivityState()
}
