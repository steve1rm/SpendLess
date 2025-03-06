package me.androidbox.spendless.authentication.presentation

import me.androidbox.spendless.core.presentation.Authentication
import me.androidbox.spendless.core.presentation.KeyButtons
import me.androidbox.spendless.core.presentation.PinMode
import kotlin.time.Duration

data class CreatePinState(
    val pinInputList: List<String> = emptyList<String>(),
    val secretPin: List<String> = emptyList<String>(),
    val pinMode: PinMode = PinMode.CREATE,
    val authentication: Authentication = Authentication.AUTHENTICATION_PROMPT,
    val shouldShowRedBanner: Boolean = false,
    val countdownTime: Duration = Duration.ZERO,
    val attempts: Int = 0,
    val enableKeyPad: Boolean = true
)
