package me.androidbox.spendless.authentication.presentation

import kotlin.time.Duration

data class CreatePinState(
    val createPinList: List<KeyButtons> = emptyList<KeyButtons>(),
    val secretPin: List<KeyButtons> = emptyList<KeyButtons>(),
    val pinMode: PinMode = PinMode.CREATE,
    val shouldShowRedBanner: Boolean = false,
    val countdownTime: Duration = Duration.ZERO
)
