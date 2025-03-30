package me.androidbox.spendless.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object AuthenticationGraph: Route

    @Serializable
    data object DashboardGraph: Route

    @Serializable
    data object SettingsGraph: Route

    @Serializable
    data object PinCreateScreen : Route

    @Serializable
    data class PinPromptScreen(val pin: String) : Route

    @Serializable
    data object LoginScreen : Route

    @Serializable
    data object RegisterScreen : Route

    @Serializable
    data object PreferenceOnBoardingScreen : Route

    @Serializable
    data class DashboardScreen(val openTransaction: Int) : Route

    @Serializable
    data object AllTransactionsScreen : Route

    @Serializable
    data object SettingsScreen : Route

    @Serializable
    data object SecurityScreen : Route

    @Serializable
    data object PreferenceSettingsScreen : Route
}