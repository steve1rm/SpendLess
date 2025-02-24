package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.presentation.RegisterAction.OnUsernameEntered

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = RegisterState()
        )

    fun action(action: RegisterAction) {
        when(action) {
            is OnUsernameEntered -> {
                _registerState.update { state ->
                    state.copy(
                        username = action.username
                    )
                }
            }

            RegisterAction.OnLoginClicked -> {
                /** no-op */
            }
            RegisterAction.OnRegisterClicked -> {
                getLoginCredentials()
            }
        }
    }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            try {
                // use case for fetch from the room db
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}