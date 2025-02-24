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

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState())
    val loginState = _loginState.asStateFlow()
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoginState()
        )

    fun action(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClicked -> {
                getLoginCredentials()
            }
            is LoginAction.OnPinEntered -> {
                _loginState.update { state ->
                    state.copy(
                        pin = action.pin
                    )
                }
            }
            is LoginAction.OnUsernameEntered -> {
                _loginState.update { state ->
                    state.copy(
                        username = action.username
                    )
                }
            }

            LoginAction.OnRegisterClicked -> {
                /** Register clicked */
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