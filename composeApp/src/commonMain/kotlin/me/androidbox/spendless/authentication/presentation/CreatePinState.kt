package me.androidbox.spendless.authentication.presentation

data class CreatePinState(
    val createPinList: List<KeyButtons> = emptyList<KeyButtons>(),
    val secretPin: List<KeyButtons> = emptyList<KeyButtons>(),
    val pinMode: PinMode = PinMode.CREATE,
    val isValidPin: Boolean = false
)
