package me.androidbox.spendless.authentication.presentation

sealed interface LoginAction {
    data class OnUsernameEntered(val username: String) : LoginAction
    data class OnPinEntered(val pin: String) : LoginAction
    data object OnLoginClicked : LoginAction
    data object OnRegisterClicked : LoginAction
}