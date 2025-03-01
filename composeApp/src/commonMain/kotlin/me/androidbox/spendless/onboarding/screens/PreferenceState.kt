package me.androidbox.spendless.onboarding.screens

import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ThousandsSeparator

data class PreferenceState(
    val decimalSeparator: DecimalSeparator = DecimalSeparator.entries.first(),
    val thousandsSeparator: ThousandsSeparator = ThousandsSeparator.entries.first(),
    val isEnabled: Boolean = false,
    val money: Long = 0L
)
