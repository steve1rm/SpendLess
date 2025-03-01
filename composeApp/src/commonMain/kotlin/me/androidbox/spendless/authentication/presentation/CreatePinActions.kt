package me.androidbox.spendless.authentication.presentation

import me.androidbox.spendless.core.presentation.KeyButtons
import me.androidbox.spendless.core.presentation.PinMode

sealed interface CreatePinActions {
    data class OnPinNumberEntered(val pinNumber: KeyButtons) : CreatePinActions
    data object OnDeletePressed : CreatePinActions
    data class ShouldShowRedBanner(val showBanner: Boolean): CreatePinActions
    data class ShouldUpdateMode(val pinMode: PinMode) : CreatePinActions
    data object OnBackPressed : CreatePinActions
}