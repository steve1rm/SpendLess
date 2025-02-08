package me.androidbox.spendless.authentication.presentation

import kotlin.time.Duration

data class CreatePinState(
    val createPinList: List<KeyButtons> = emptyList<KeyButtons>(),
    val secretPin: List<KeyButtons> = emptyList<KeyButtons>(),
    val pinMode: PinMode = PinMode.CREATE,
    val authentication: Authentication = Authentication.AUTHENTICATION_PROMPT,
    val shouldShowRedBanner: Boolean = false,
    val countdownTime: Duration = Duration.ZERO,
    val attempts: Int = 0,
    val enableKeyPad: Boolean = true
)
