package me.androidbox.spendless.dashboard

sealed interface DashboardEvents {
    data class OpenPinPromptScreen(val pin: String) : DashboardEvents
    data object OpenAllTransactionsScreen : DashboardEvents
}