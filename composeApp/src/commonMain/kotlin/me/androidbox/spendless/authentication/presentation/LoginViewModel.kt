package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.domain.GetUserUseCase
import me.androidbox.spendless.authentication.domain.ValidateUserUseCase
import me.androidbox.spendless.authentication.domain.imp.generatePinDigest
import me.androidbox.spendless.core.presentation.showRedBannerForDuration
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class LoginViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val validateUserUseCase: ValidateUserUseCase
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

            is LoginAction.ShouldShowRedBanner -> {
                showRedBannerForDuration(2.seconds)
                    .onEach { shouldShow ->
                        _loginState.update { state ->
                            state.copy(
                                shouldShowRedBanner = shouldShow
                            )
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            try {
                val pin = generatePinDigest(loginState.value.username, loginState.value.pin)

                when(validateUserUseCase.execute(loginState.value.username, pin)) {
                    null -> {
                        println("User doesn't exist")
                        _loginChannel.send(LoginEvent.OnLoginFailure)
                    }
                    else -> {
                        _loginChannel.send(LoginEvent.OnLoginSuccess)
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