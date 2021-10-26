package com.ezanetta.lasttwenty.notifications.presentation.view

import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.ezanetta.lasttwenty.R
import com.ezanetta.lasttwenty.notifications.presentation.activities.NotificationsActivity
import com.ezanetta.lasttwenty.notifications.presentation.data.FakeNotificationsDataSource
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationItem
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationsActivityState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [Build.VERSION_CODES.P])
class NotificationsActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var shadowActivity: NotificationsActivity

    @Before
    fun setup() {
        hiltRule.inject()

        shadowActivity =
            Robolectric.buildActivity(NotificationsActivity::class.java).create().resume().get()
    }

    @Test
    fun `processActivityState SHOULD show the empty state WHEN NotificationsActivityState is ShowEmptyState`() {
        // GIVEN
        val emptyState = shadowActivity.findViewById<TextView>(R.id.empty_state)
        val notifications = shadowActivity.findViewById<RecyclerView>(R.id.notifications)
        val enableNotifications = shadowActivity.findViewById<Button>(R.id.enable_notifications)

        // WHEN
        shadowActivity.processActivityState(NotificationsActivityState.ShowEmptyState)

        // THEN
        assertTrue(emptyState.visibility == View.VISIBLE)
        assertTrue(notifications.visibility == View.GONE)
        assertTrue(enableNotifications.visibility == View.GONE)
    }

    @Test
    fun `processActivityState SHOULD show the notification list WHEN NotificationsActivityState is ShowNotifications`() {
        // GIVEN
        val emptyState = shadowActivity.findViewById<TextView>(R.id.empty_state)
        val notifications = shadowActivity.findViewById<RecyclerView>(R.id.notifications)
        val enableNotifications = shadowActivity.findViewById<Button>(R.id.enable_notifications)
        val showActiveNotifications =
            shadowActivity.findViewById<SwitchCompat>(R.id.show_active_notifications)

        // WHEN
        shadowActivity.processActivityState(
            NotificationsActivityState.ShowNotifications(
                FakeNotificationsDataSource.allNotificationItems as ArrayList<NotificationItem>
            )
        )

        // THEN
        assertTrue(emptyState.visibility == View.GONE)
        assertTrue(enableNotifications.visibility == View.GONE)
        assertTrue(showActiveNotifications.visibility == View.VISIBLE)
        assertTrue(notifications.visibility == View.VISIBLE)
    }

    @Test
    fun `processActivityState SHOULD show enable notifications button WHEN NotificationsActivityState is ShowRequestNotificationPermissionState`() {
        // GIVEN
        val emptyState = shadowActivity.findViewById<TextView>(R.id.empty_state)
        val notifications = shadowActivity.findViewById<RecyclerView>(R.id.notifications)
        val enableNotifications = shadowActivity.findViewById<Button>(R.id.enable_notifications)
        val showActiveNotifications =
            shadowActivity.findViewById<SwitchCompat>(R.id.show_active_notifications)

        // WHEN
        shadowActivity
            .processActivityState(NotificationsActivityState.ShowRequestNotificationPermissionState)

        // THEN
        assertTrue(emptyState.visibility == View.GONE)
        assertTrue(showActiveNotifications.visibility == View.GONE)
        assertTrue(notifications.visibility == View.GONE)
        assertTrue(enableNotifications.visibility == View.VISIBLE)
    }

    @Test
    fun `processActivityState SHOULD show the notification list WHEN NotificationsActivityState is ShowActiveNotifications`() {
        // GIVEN
        val emptyState = shadowActivity.findViewById<TextView>(R.id.empty_state)
        val notifications = shadowActivity.findViewById<RecyclerView>(R.id.notifications)
        val enableNotifications = shadowActivity.findViewById<Button>(R.id.enable_notifications)
        val showActiveNotifications =
            shadowActivity.findViewById<SwitchCompat>(R.id.show_active_notifications)

        // WHEN
        shadowActivity.processActivityState(
            NotificationsActivityState.ShowActiveNotifications(
                FakeNotificationsDataSource.activeNotificationItems as ArrayList<NotificationItem>
            )
        )

        // THEN
        assertTrue(emptyState.visibility == View.GONE)
        assertTrue(enableNotifications.visibility == View.GONE)
        assertTrue(showActiveNotifications.visibility == View.VISIBLE)
        assertTrue(showActiveNotifications.isChecked)
        assertTrue(notifications.visibility == View.VISIBLE)
    }

}