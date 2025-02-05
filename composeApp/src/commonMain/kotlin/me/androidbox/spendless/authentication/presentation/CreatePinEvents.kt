package me.androidbox.spendless.authentication.presentation

sealed interface CreatePinEvents {
    data class PinEntryEvent(val isValid: Boolean) : CreatePinEvents
}