package me.androidbox.spendless.onboarding.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

class OnboardingPreferenceViewModel : ViewModel() {
    private val _onboardingPreferenceState = MutableStateFlow(OnboardingPreferenceState())
    val onboardingPreferenceState = _onboardingPreferenceState.asStateFlow()

    init {
        combine(
            onboardingPreferenceState.distinctUntilChangedBy { it.decimalSeparator },
            onboardingPreferenceState.distinctUntilChangedBy { it.thousandsSeparator }) { decimalSeparator, thousandSeparator ->
            _onboardingPreferenceState.update { onboardingPreferenceState ->
                onboardingPreferenceState.copy(
                    isEnabled = decimalSeparator.decimalSeparator.type != thousandSeparator.thousandsSeparator.type
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: OnboardingPreferenceAction) {
        when(action) {
            OnboardingPreferenceAction.OnStartTracking -> {
                // Create user
                // Save preferences
            }

            is OnboardingPreferenceAction.OnDecimalSeparatorSelected -> {
                _onboardingPreferenceState.update { onboardingPreferenceState ->
                    onboardingPreferenceState.copy(
                        decimalSeparator = action.decimalSeparator
                    )
                }
            }
            is OnboardingPreferenceAction.OnThousandsSeparatorSelected -> {
                _onboardingPreferenceState.update { onboardingPreferenceState ->
                    onboardingPreferenceState.copy(
                        thousandsSeparator = action.thousandsSeparator
                    )
                }
            }
        }
    }
}