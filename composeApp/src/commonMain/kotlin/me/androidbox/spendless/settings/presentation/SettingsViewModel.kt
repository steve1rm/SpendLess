package me.androidbox.spendless.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.androidbox.spendless.SpendLessPreference

class SettingsViewModel(
    private val spendLessPreference: SpendLessPreference
) : ViewModel() {

    fun clearPreferences() {
        viewModelScope.launch {
            spendLessPreference.clearAll()
        }
    }
}