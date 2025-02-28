package me.androidbox.spendless.authentication.presentation

sealed interface RegisterAction {
    data class OnUsernameEntered(val username: String) : RegisterAction
    data object OnLoginClicked : RegisterAction
    data object OnRegisterClicked : RegisterAction
}