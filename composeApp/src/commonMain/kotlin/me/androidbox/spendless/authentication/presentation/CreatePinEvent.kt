package me.androidbox.spendless.authentication.presentation

sealed interface CreatePinEvent {
    data class PinEntryEvent(val isValid: Boolean, val pin: String) : CreatePinEvent
    data class IsAuthenticated(val isAuthenticated: Boolean) : CreatePinEvent
}