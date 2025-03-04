package me.androidbox.spendless.authentication.presentation

data class LoginState(
    val username: String = "",
    val pin: String = "",
    val shouldShowRedBanner: Boolean = false
)
