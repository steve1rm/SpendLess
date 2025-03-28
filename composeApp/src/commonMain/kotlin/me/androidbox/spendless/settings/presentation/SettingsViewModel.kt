package me.androidbox.spendless.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.androidbox.spendless.SpendLessPreference
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase

class SettingsViewModel(
    private val spendLessPreference: SpendLessPreference,
    private val insertPreferenceUseCase: InsertPreferenceUseCase
) : ViewModel() {

    private val _preferencesSettingsState = MutableStateFlow(PreferenceState())
    val preferencesSettingsState = _preferencesSettingsState.asStateFlow()

    private val _preferenceChannel = Channel<SettingsEvent>()
    val preferenceChannel = _preferenceChannel.receiveAsFlow()

    fun settingsAction(settingsAction: SettingsAction) {
        when(settingsAction) {
            SettingsAction.OnLogout -> {
                viewModelScope.launch {
                    spendLessPreference.clearAll()
                    println("CLEAR HAS DONE")
                    _preferenceChannel.send(SettingsEvent.OnLogoutSuccess)
                    println("SEND HAS DONE")
                }
            }
        }
    }
}