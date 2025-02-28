package me.androidbox.spendless.authentication.presentation

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data object OnLoginFailure : LoginEvent
}