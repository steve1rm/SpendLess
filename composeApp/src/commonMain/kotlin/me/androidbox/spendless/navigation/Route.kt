package me.androidbox.spendless.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthenticationGraph: Route

    @Serializable
    data object PinCreateScreen : Route

    @Serializable
    data object PinPromptScreen : Route


    @Serializable
    data object PreferenceScreen : Route

    @Serializable
    data object DashboardScreen : Route
}