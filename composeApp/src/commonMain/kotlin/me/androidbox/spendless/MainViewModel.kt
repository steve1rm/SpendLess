package me.androidbox.spendless

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val _mainState = MutableStateFlow<MainState>(MainState())
    val mainState = _mainState.asStateFlow()
        .onStart {
            if(!hasCompleted) {
                getLoginCredentials()
                hasCompleted = true
            }
        }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            _mainState.update { state -> state.copy(isLoading = true) }
            val username = spendLessPreference.getUsername()

            username?.let { _ ->
                _mainState.update { state ->
                    state.copy(
                        isSessionActive = hasActiveSession(spendLessPreference.getTimeStamp()),
                        isLoading = false)
                }
            } ?: run {
                _mainState.update { mainState ->
                    mainState.copy(
                        hasUsername = false,
                        isSessionActive = false,
                        isLoading = false
                    )
                }
            }
        }
    }
}