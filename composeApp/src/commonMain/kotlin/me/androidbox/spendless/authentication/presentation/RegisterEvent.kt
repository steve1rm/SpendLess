package me.androidbox.spendless.authentication.presentation

sealed interface RegisterEvent {
    data class OnRegisterSuccess(val username: String) : RegisterEvent
    data object OnRegisterFailure : RegisterEvent
}