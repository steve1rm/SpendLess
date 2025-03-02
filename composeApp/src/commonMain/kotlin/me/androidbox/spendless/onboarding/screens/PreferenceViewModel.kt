package me.androidbox.spendless.onboarding.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase

class PreferenceViewModel(
    private val insertPreferenceUseCase: InsertPreferenceUseCase
) : ViewModel() {
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

            is PreferenceAction.OnCurrency -> {
                _preferenceState.update { preferenceState ->
                    preferenceState.copy(
                        currency = action.currency
                    )
                }
            }

            is PreferenceAction.OnExpensesFormat -> {
                _preferenceState.update { preferenceState ->
                    preferenceState.copy(
                        expensesFormat = action.expensesFormat
                    )
                }
            }
        }
    }

    fun savePreferences() {
        viewModelScope.launch {
            insertPreferenceUseCase.execute(
                PreferenceTable(
                    expensesFormat = preferenceState.value.expensesFormat.ordinal,
                    currency = preferenceState.value.currency.ordinal,
                    decimalSeparator = preferenceState.value.decimalSeparator.ordinal,
                    thousandsSeparator = preferenceState.value.thousandsSeparator.ordinal
                )
            )
        }
    }
}