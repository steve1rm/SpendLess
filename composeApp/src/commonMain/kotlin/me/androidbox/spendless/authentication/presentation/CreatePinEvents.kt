package me.androidbox.spendless.authentication.presentation

sealed interface CreatePinEvents {
    data class IncorrectPinEvent(val isValid: Boolean) : CreatePinEvents
}