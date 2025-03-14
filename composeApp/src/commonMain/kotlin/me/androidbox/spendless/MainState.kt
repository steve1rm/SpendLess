package me.androidbox.spendless

sealed class MainState {
    data class Success(
        val hasUsername: Boolean = false,
        val isSessionActive: Boolean = false) : MainState()

    data object Loading : MainState()
}
