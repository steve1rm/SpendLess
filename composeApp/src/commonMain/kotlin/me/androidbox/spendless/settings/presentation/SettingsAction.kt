package me.androidbox.spendless.settings.presentation

sealed interface SettingsAction {
    data object OnLogout : SettingsAction
}