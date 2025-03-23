package me.androidbox.spendless.settings.presentation

sealed interface SettingsEvent {
    data object OnLogoutSuccess : SettingsEvent
}