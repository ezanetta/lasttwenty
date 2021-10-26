package com.ezanetta.lasttwenty.notifications.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ezanetta.lasttwenty.TestCoroutineRule
import com.ezanetta.lasttwenty.extensions.getOrAwaitValue
import com.ezanetta.lasttwenty.notifications.data.db.Notification
import com.ezanetta.lasttwenty.notifications.domain.usecase.GetNotificationsUseCase
import com.ezanetta.lasttwenty.notifications.presentation.data.FakeNotificationsDataSource.activeNotificationItems
import com.ezanetta.lasttwenty.notifications.presentation.data.FakeNotificationsDataSource.allNotificationItems
import com.ezanetta.lasttwenty.notifications.presentation.data.FakeNotificationsDataSource.allNotifications
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationsActivityState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class NotificationsViewModelTest {
    private val getNotificationUseCase: GetNotificationsUseCase = mockk()
    private val notificationsViewModel = NotificationsViewModel(getNotificationUseCase)

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `fetchNotifications SHOULD call notificationActivityState liveData with ShowNotifications value WHEN appHasNotificationsPermission is true`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            notificationsViewModel.appHasNotificationsPermission = true

            coEvery {
                getNotificationUseCase.getNotifications()
            } returns notifications

            // WHEN
            notificationsViewModel.fetchNotifications()

            // THEN
            val result = notificationsViewModel.notificationsActivityState.getOrAwaitValue()
            assertTrue(result is NotificationsActivityState.ShowNotifications)
            assertEquals(
                (result as NotificationsActivityState.ShowNotifications).notifications,
                allNotificationItems
            )
        }
    }

    @Test
    fun `fetchNotifications SHOULD call notificationActivityState liveData with ShowEmptyState value WHEN appHasNotificationsPermission is true and there is no notifications`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            notificationsViewModel.appHasNotificationsPermission = true

            coEvery {
                getNotificationUseCase.getNotifications()
            } returns emptyNotifications

            // WHEN
            notificationsViewModel.fetchNotifications()

            // THEN
            val result = notificationsViewModel.notificationsActivityState.getOrAwaitValue()
            assertTrue(result is NotificationsActivityState.ShowEmptyState)
        }
    }

    @Test
    fun `fetchNotifications SHOULD call notificationActivityState liveData with ShowRequestNotificationPermissionState value WHEN appHasNotificationsPermission is false`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            notificationsViewModel.appHasNotificationsPermission = false

            // WHEN
            notificationsViewModel.fetchNotifications()

            // THEN
            assertTrue(
                notificationsViewModel.notificationsActivityState.getOrAwaitValue()
                        is NotificationsActivityState.ShowRequestNotificationPermissionState
            )
        }
    }

    @Test
    fun `showActiveNotificationsListener SHOULD call notificationActivityState liveData with ShowActiveNotifications value WHEN isChecked is true`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            notificationsViewModel.appHasNotificationsPermission = true
            val isChecked = true

            coEvery {
                getNotificationUseCase.getNotifications()
            } returns notifications

            notificationsViewModel.fetchNotifications()

            // WHEN
            notificationsViewModel.showActiveNotificationsListener(isChecked)

            // THEN
            val result = notificationsViewModel.notificationsActivityState.getOrAwaitValue()
            assertTrue(result is NotificationsActivityState.ShowActiveNotifications)
            assertEquals(
                (result as NotificationsActivityState.ShowActiveNotifications).notifications,
                activeNotificationItems
            )
        }
    }

    @Test
    fun `showActiveNotificationsListener SHOULD call notificationActivityState liveData with ShowNotifications value WHEN isChecked is false`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            notificationsViewModel.appHasNotificationsPermission = true
            val isChecked = false

            coEvery {
                getNotificationUseCase.getNotifications()
            } returns notifications

            notificationsViewModel.fetchNotifications()

            // WHEN
            notificationsViewModel.showActiveNotificationsListener(isChecked)

            // THEN
            val result = notificationsViewModel.notificationsActivityState.getOrAwaitValue()
            assertTrue(result is NotificationsActivityState.ShowNotifications)
            assertEquals(
                (result as NotificationsActivityState.ShowNotifications).notifications,
                allNotificationItems
            )
        }
    }

    private companion object {
        val notifications: Flow<List<Notification>> = flow {
            emit(
                allNotifications
            )
        }

        val emptyNotifications: Flow<List<Notification>> = flow {
            emit(
                emptyList()
            )
        }
    }
}