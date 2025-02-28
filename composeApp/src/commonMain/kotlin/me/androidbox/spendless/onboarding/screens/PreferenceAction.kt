package me.androidbox.spendless.onboarding.screens

import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ThousandsSeparator

sealed interface PreferenceAction {
    data object OnStartTracking : PreferenceAction
    data class OnDecimalSeparatorSelected(val decimalSeparator: DecimalSeparator) : PreferenceAction
    data class OnThousandsSeparatorSelected(val thousandsSeparator: ThousandsSeparator) : PreferenceAction
}
