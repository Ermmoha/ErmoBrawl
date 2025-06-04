package com.example.ermobrawl2.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationDestination {
    @Serializable
    data class Home(val selectCategory: String, val titleCategory: String) : NavigationDestination

    @Serializable
    data object Card : NavigationDestination

    @Serializable
    data class News(val id: String) : NavigationDestination
}