package me.androidbox.spendless.onboarding.screens

import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ThousandsSeparator

sealed interface OnboardingPreferenceAction {
    data object OnStartTracking : OnboardingPreferenceAction
    data class OnDecimalSeparatorSelected(val decimalSeparator: DecimalSeparator) : OnboardingPreferenceAction
    data class OnThousandsSeparatorSelected(val thousandsSeparator: ThousandsSeparator) : OnboardingPreferenceAction
}
