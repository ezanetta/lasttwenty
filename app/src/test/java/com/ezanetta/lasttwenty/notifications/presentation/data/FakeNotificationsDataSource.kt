package com.ezanetta.lasttwenty.notifications.presentation.data

import com.ezanetta.lasttwenty.notifications.data.db.Notification
import com.ezanetta.lasttwenty.notifications.presentation.model.NotificationItem

object FakeNotificationsDataSource {

    private val rickAndMortyNotification = Notification(
        1, "Rick and Morty App",
        "Look at me Mort, I'm a notification",
        "com.rickandmorty.app",
        1635258857020,
        true
    )

    private val pokemonNotification = Notification(
        1, "Pokemon app",
        "Pikappchu",
        "com.pokemon.app",
        1635258857020,
        true
    )

    private val simpsonsNotification = Notification(
        1, "Simpsons app",
        "Doh",
        "com.simpsons.app",
        1635258857020,
        false
    )

    val allNotifications = arrayListOf(
        rickAndMortyNotification,
        pokemonNotification,
        simpsonsNotification
    )

    val activeNotificationItems = allNotifications.filter { it.active }.map {
        NotificationItem(it.title, it.text, it.packageName, it.active)
    }

    val allNotificationItems = allNotifications.map {
        NotificationItem(it.title, it.text, it.packageName, it.active)
    }

}