package me.androidbox.spendless

data class MainState(
    val hasUsername: Boolean = false,
    val isSessionActive: Boolean = false,
    val isLoading: Boolean = false
)
