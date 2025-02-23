package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val _authenticationState = MutableStateFlow(AuthenticationState())
    val authenticationState = _authenticationState
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AuthenticationState()
        )

    fun action(action: AuthenticationAction) {
        when(action) {
            AuthenticationAction.OnLogin -> {
                getLoginCredentials()
            }
            is AuthenticationAction.OnPinEntered -> {
                _authenticationState.update { state ->
                    state.copy(
                        pin = action.pin
                    )
                }
            }
            is AuthenticationAction.OnUsernameEntered -> {
                _authenticationState.update { state ->
                    state.copy(
                        username = action.username
                    )
                }
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