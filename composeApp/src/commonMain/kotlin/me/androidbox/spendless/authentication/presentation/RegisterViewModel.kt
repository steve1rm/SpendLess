package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.domain.GetUserUseCase
import me.androidbox.spendless.authentication.presentation.RegisterAction.OnUsernameEntered
import me.androidbox.spendless.core.presentation.showRedBannerForDuration
import kotlin.time.Duration.Companion.seconds

class RegisterViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _registerChannel = Channel<RegisterEvent>()
    val registerChannel = _registerChannel.receiveAsFlow()

    init {
        registerState.distinctUntilChangedBy { it.username }
            .map {
                it.username.isNotEmpty()
            }
            .onEach { isValid ->
                _registerState.update { registerState ->
                    registerState.copy(
                        canRegister = isValid
                    )
                }
            }
            .launchIn(viewModelScope)
    }

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
                /** Check that the login name does exist */
                getLoginCredentials()
            }

            is RegisterAction.ShouldShowRedBanner -> {
                showRedBannerForDuration(2.seconds)
                    .onEach { shouldShow ->
                        _registerState.update { state ->
                            state.copy(shouldShowRedBanner = shouldShow)
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            try {
                if(getUserUseCase.execute(registerState.value.username) == null) {
                    _registerChannel.send(RegisterEvent.OnRegisterSuccess(username = registerState.value.username))
                }
                else {
                    _registerChannel.send(RegisterEvent.OnRegisterFailure)
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
                _registerChannel.send(RegisterEvent.OnRegisterFailure)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared REGISTER_VIEWMODEL")
    }
}