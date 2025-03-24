package me.androidbox.spendless.onboarding.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.SpendLessPreference
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.FetchPreferenceUseCase
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase
import me.androidbox.spendless.transactions.domain.FetchTotalTransactionAmountUseCase

class PreferenceViewModel(
    private val insertPreferenceUseCase: InsertPreferenceUseCase,
    private val fetchPreferenceUseCase: FetchPreferenceUseCase,
    private val fetchTotalTransactionAmountUseCase: FetchTotalTransactionAmountUseCase,
    private val spendLessPreference: SpendLessPreference,
    private val applicationScope: CoroutineScope
) : ViewModel() {

    private var hasFetched = false

    private val _preferenceState = MutableStateFlow(PreferenceState())
    val preferenceState = _preferenceState.asStateFlow()
        .onStart {
            if(!hasFetched) {
                fetchPreferences()
                fetchTotalTransactionAmount()
                hasFetched = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PreferenceState()
        )

    private val _preferenceChannel = Channel<PreferenceEvent>()
    val preferenceChannel = _preferenceChannel.receiveAsFlow()

    init {
        combine(
            preferenceState.distinctUntilChangedBy { it.decimalSeparator },
            preferenceState.distinctUntilChangedBy { it.thousandsSeparator }) { decimalSeparator, thousandSeparator ->
            _preferenceState.update { onboardingPreferenceState ->
                /** Only enabled the button when the decimal and thousands are not the same */
                onboardingPreferenceState.copy(
                    isEnabled = decimalSeparator.decimalSeparator.type != thousandSeparator.thousandsSeparator.type
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchTotalTransactionAmount() {
        viewModelScope.launch {
            fetchTotalTransactionAmountUseCase.execute().collect { totalAmount ->
                _preferenceState.update { dashboardState ->
                    dashboardState.copy(
                        money = totalAmount
                    )
                }
            }
        }
    }

    private fun fetchPreferences() {
        viewModelScope.launch {
            fetchPreferenceUseCase.execute().collectLatest { preferences ->
                _preferenceState.update { preferenceState ->
                    preferenceState.copy(
                        decimalSeparator = DecimalSeparator.entries[preferences.decimalSeparator],
                        thousandsSeparator = ThousandsSeparator.entries[preferences.thousandsSeparator],
                        currency = Currency.entries[preferences.currency],
                        expensesFormat = ExpensesFormat.entries[preferences.expensesFormat]
                    )
                }
            }
        }
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

            PreferenceAction.OnSavePreferences -> {
                viewModelScope.launch {
                    applicationScope.launch {
                        savePreferences()
                    }.join()

                    println("SEND CHANNEL DONE")
                    _preferenceChannel.send(PreferenceEvent.OnSavePreferences)
                }
            }
        }
    }

    private suspend fun savePreferences() {
            // (5_000) // testing
            insertPreferenceUseCase.execute(
                PreferenceTable(
                    id = 1,
                    expensesFormat = preferenceState.value.expensesFormat.ordinal,
                    currency = preferenceState.value.currency.ordinal,
                    decimalSeparator = preferenceState.value.decimalSeparator.ordinal,
                    thousandsSeparator = preferenceState.value.thousandsSeparator.ordinal
                )
            )
            println("SAVE PREFERENCES DONE")
    }

    /** TODO ADD TO THE SETTINGS VIEWMODEL */
    private fun clearSharedPreferences() {
        viewModelScope.launch {
            spendLessPreference.clearAll()
            delay(2_000)
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("PREFERENCE VIEWMODEL CLEARED")
    }
}