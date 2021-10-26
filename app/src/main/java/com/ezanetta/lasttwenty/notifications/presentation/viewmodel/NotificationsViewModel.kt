package com.ezanetta.lasttwenty.notifications.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezanetta.lasttwenty.notifications.domain.usecase.GetNotificationsUseCase
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationItem
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationsActivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    var appHasNotificationsPermission = false
    val notificationsActivityState: LiveData<NotificationsActivityState>
        get() = _notificationsActivityState
    private val _notificationsActivityState: MutableLiveData<NotificationsActivityState> =
        MutableLiveData()
    private val allNotifications: MutableList<NotificationItem> = mutableListOf()
    private var showActiveNotifications = false

    fun fetchNotifications() {
        if (appHasNotificationsPermission) {
            if (allNotifications.isNotEmpty()) {
                restorePreviousNotifications()
            } else {
                viewModelScope.launch {
                    getNotifications()
                }
            }
        } else {
            _notificationsActivityState
                .postValue(NotificationsActivityState.ShowRequestNotificationPermissionState)
        }
    }

    private fun restorePreviousNotifications() {
        if (showActiveNotifications) {
            showActiveNotifications()
        } else {
            showAllNotifications()
        }
    }

    private suspend fun getNotifications() {
        getNotificationsUseCase.getNotifications().collect { notifications ->
            if (notifications.isNotEmpty()) {
                notifications.map { notification ->
                    NotificationItem(
                        notification.title,
                        notification.text,
                        notification.packageName,
                        notification.active
                    )
                }.also { notificationItems ->
                    allNotifications.clear()
                    allNotifications.addAll(notificationItems)
                    showNotificationsState()
                }
            } else {
                _notificationsActivityState
                    .postValue(NotificationsActivityState.ShowEmptyState)
            }
        }
    }

    private fun showNotificationsState() {
        if (showActiveNotifications) {
            showActiveNotifications()
        } else {
            showAllNotifications()
        }
    }

    private fun showActiveNotifications() {
        showActiveNotifications = true
        val activeNotifications = allNotifications.filter { it.active }

        if (activeNotifications.isNotEmpty()) {
            _notificationsActivityState.postValue(
                NotificationsActivityState
                    .ShowNotifications(activeNotifications as ArrayList<NotificationItem>)
            )
        } else {
            _notificationsActivityState
                .postValue(NotificationsActivityState.ShowEmptyState)
        }
    }

    private fun showAllNotifications() {
        showActiveNotifications = false

        if (allNotifications.isNotEmpty()) {
            _notificationsActivityState.postValue(
                NotificationsActivityState
                    .ShowNotifications(allNotifications as ArrayList<NotificationItem>)
            )
        } else {
            _notificationsActivityState
                .postValue(NotificationsActivityState.ShowEmptyState)
        }
    }

    fun showActiveNotificationsListener(isChecked: Boolean) {
        if (isChecked) {
            showActiveNotifications()
        } else {
            showAllNotifications()
        }
    }
}