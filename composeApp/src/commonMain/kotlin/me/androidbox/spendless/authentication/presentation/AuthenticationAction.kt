package me.androidbox.spendless.authentication.presentation

sealed interface AuthenticationAction {
    data class OnUsernameEntered(val username: String) : AuthenticationAction
    data class OnPinEntered(val pin: String) : AuthenticationAction
    data object OnLogin : AuthenticationAction
}