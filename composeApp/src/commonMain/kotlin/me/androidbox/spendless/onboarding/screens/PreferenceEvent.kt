package me.androidbox.spendless.onboarding.screens

sealed interface PreferenceEvent {
    data object OnSavePreferences : PreferenceEvent
}
