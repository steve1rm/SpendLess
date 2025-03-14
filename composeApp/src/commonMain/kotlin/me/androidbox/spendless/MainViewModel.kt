package me.androidbox.spendless

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.core.presentation.hasActiveSession

class MainViewModel(
    private val spendLessPreference: SpendLessPreference,
) : ViewModel() {
    private var hasCompleted = false

    private val _mainState = MutableStateFlow<MainState>(MainState.Loading)
    val mainState = _mainState.asStateFlow()
        .onStart {
            if(!hasCompleted) {
                getLoginCredentials()
                hasCompleted = true
            }
        }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            _mainState.value = MainState.Loading

            val username = spendLessPreference.getUsername()
            delay(3_000L)

            username?.let { _ ->
                _mainState.value = MainState.Success(
                    isSessionActive = hasActiveSession(spendLessPreference.getTimeStamp()),
                    hasUsername = true
                )
            } ?: run {
                _mainState.value = MainState.Success(
                    isSessionActive = false,
                    hasUsername = false
                )
            }
        }
    }
}