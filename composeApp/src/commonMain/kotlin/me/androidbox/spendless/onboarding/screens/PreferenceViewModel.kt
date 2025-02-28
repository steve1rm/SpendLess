package me.androidbox.spendless.onboarding.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

class PreferenceViewModel : ViewModel() {
    private val _preferenceState = MutableStateFlow(PreferenceState())
    val preferenceState = _preferenceState.asStateFlow()

    init {
        combine(
            preferenceState.distinctUntilChangedBy { it.decimalSeparator },
            preferenceState.distinctUntilChangedBy { it.thousandsSeparator }) { decimalSeparator, thousandSeparator ->
            _preferenceState.update { onboardingPreferenceState ->
                onboardingPreferenceState.copy(
                    isEnabled = decimalSeparator.decimalSeparator.type != thousandSeparator.thousandsSeparator.type
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: PreferenceAction) {
        when(action) {
            PreferenceAction.OnStartTracking -> {
                // Create user
                // Save preferences
            }

            is PreferenceAction.OnDecimalSeparatorSelected -> {
                _preferenceState.update { preferenceState ->
                    preferenceState.copy(
                        decimalSeparator = action.decimalSeparator
                    )
                }
            }
            is PreferenceAction.OnThousandsSeparatorSelected -> {
                _preferenceState.update { preferenceState ->
                    preferenceState.copy(
                        thousandsSeparator = action.thousandsSeparator
                    )
                }
            }
        }
    }
}