package com.ezanetta.lasttwenty.notifications.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.core.app.NotificationManagerCompat
import com.ezanetta.lasttwenty.R
import com.ezanetta.lasttwenty.databinding.ActivityNotificationsBinding
import com.ezanetta.lasttwenty.notifications.extensions.hide
import com.ezanetta.lasttwenty.notifications.extensions.show
import com.ezanetta.lasttwenty.notifications.presentation.adapter.NotificationsAdapter
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationsActivityState
import com.ezanetta.lasttwenty.notifications.presentation.viewmodel.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsActivity : AppCompatActivity() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val notificationsAdapter by lazy { NotificationsAdapter() }
    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setListeners()
        setupObserver()
        setAppHasNotificationsPermissions()
        startNotificationsService()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setAppHasNotificationsPermissions()
        fetchNotifications()
    }

    private fun setupObserver() {
        notificationsViewModel.notificationsActivityState.observe(this, ::processActivityState)
    }

    private fun fetchNotifications() {
        notificationsViewModel.fetchNotifications()
    }

    private fun setListeners() {
        binding.enableNotifications.setOnClickListener {
            startActivity(Intent(NOTIFICATION_SETTINGS_LISTENER_ACTION))
        }

        binding.showActiveNotifications.setOnCheckedChangeListener { _, isChecked ->
            notificationsViewModel.showActiveNotificationsListener(isChecked)
        }
    }

    private fun processActivityState(state: NotificationsActivityState) {
        when (state) {
            NotificationsActivityState.ShowEmptyState -> {
                with(binding) {
                    notifications.hide()
                    enableNotifications.hide()
                    emptyState.show()
                }
            }

            is NotificationsActivityState.ShowNotifications -> {
                with(binding) {
                    notifications.adapter = notificationsAdapter
                    notificationsAdapter.addAll(state.notifications)
                    notifications.show()
                    emptyState.hide()
                    enableNotifications.hide()
                    showActiveNotifications.show()
                }
            }

            is NotificationsActivityState.ShowActiveNotifications -> {
                with(binding) {
                    notificationsAdapter.addAll(state.notifications)
                    notifications.show()
                    emptyState.hide()
                    enableNotifications.hide()
                    showActiveNotifications.show()
                }
            }
            NotificationsActivityState.ShowRequestNotificationPermissionState -> {
                with(binding) {
                    notifications.hide()
                    emptyState.hide()
                    showActiveNotifications.hide()
                    enableNotifications.show()
                }
            }
        }
    }

    private fun setAppHasNotificationsPermissions() {
        notificationsViewModel.appHasNotificationsPermission = NotificationManagerCompat
            .getEnabledListenerPackages(this).contains(packageName)
    }

    private fun startNotificationsService() {
        val notificationsService = Intent(
            this,
            NotificationListenerService::class.java
        )

        startService(notificationsService)
    }

    private companion object {
        const val NOTIFICATION_SETTINGS_LISTENER_ACTION =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }
}