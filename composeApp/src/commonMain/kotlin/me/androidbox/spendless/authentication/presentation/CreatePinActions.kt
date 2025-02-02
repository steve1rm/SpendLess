package me.androidbox.spendless.authentication.presentation

sealed interface CreatePinActions {
    data class OnPinNumberEntered(val pinNumber: KeyButtons) : CreatePinActions
    data object OnDeletePressed : CreatePinActions
}