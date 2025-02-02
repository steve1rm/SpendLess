package me.androidbox.spendless.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthenticationGraph: Route

    @Serializable
    data object PinCreateScreen : Route
}