package me.androidbox.spendless.authentication.presentation

data class CreatePinState(
    val createPinList: List<KeyButtons> = emptyList<KeyButtons>()
)
