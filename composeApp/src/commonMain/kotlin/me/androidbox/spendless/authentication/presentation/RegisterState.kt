package me.androidbox.spendless.authentication.presentation

data class RegisterState(
    val username: String = "",
    val canRegister: Boolean = false
)
