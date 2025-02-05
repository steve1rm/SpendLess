package me.androidbox.spendless.authentication.presentation

sealed interface CreatePinEvents {
    data class HasInvalidPin(val isValid: Boolean) : CreatePinEvents
}