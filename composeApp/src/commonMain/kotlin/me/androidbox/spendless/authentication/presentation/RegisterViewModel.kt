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
import me.androidbox.spendless.authentication.presentation.RegisterAction.OnUsernameEntered

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow(RegisterState())
    val registerState = _registerState.asStateFlow()

    private val _registerChannel = Channel<RegisterEvent>()
    val registerChannel = _registerChannel.consumeAsFlow()

    init {
        registerState.distinctUntilChangedBy { it.username }
            .map {
                it.username.count() >= 1
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
                /** Check that the login name doesn't exist */
                getLoginCredentials()
            }
        }
    }

    private fun getLoginCredentials() {
        viewModelScope.launch {
            try {
                // use case for fetch from the room db
                _registerChannel.send(RegisterEvent.OnRegisterSuccess)
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