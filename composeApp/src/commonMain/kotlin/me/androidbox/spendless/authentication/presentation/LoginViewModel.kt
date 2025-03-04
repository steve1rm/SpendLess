package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.domain.GetUserUseCase

class LoginViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState())
    val loginState = _loginState.asStateFlow()
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoginState()
        )

    private val _loginChannel = Channel<LoginEvent>()
    val loginChannel = _loginChannel.receiveAsFlow()

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
                when(val user = getUserUseCase.execute(loginState.value.username)) {
                    null -> {
                        println("User doesn't exist")
                        _loginChannel.send(LoginEvent.OnLoginFailure)
                    }
                    else -> {
                        if(user.pin == loginState.value.pin) {
                            _loginChannel.send(LoginEvent.OnLoginSuccess)
                        }
                        else {
                            _loginChannel.send(LoginEvent.OnLoginFailure)
                        }
                    }
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared LOGIN_VIEWMODEL")
    }
}