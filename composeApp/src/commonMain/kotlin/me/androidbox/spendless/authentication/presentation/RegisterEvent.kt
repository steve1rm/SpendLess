package me.androidbox.spendless.authentication.presentation

sealed interface RegisterEvent {
    data object OnRegisterSuccess : RegisterEvent
    data object OnRegisterFailure : RegisterEvent
}