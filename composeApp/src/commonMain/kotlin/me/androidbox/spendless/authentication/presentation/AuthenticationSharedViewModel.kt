package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase

class AuthenticationSharedViewModel(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    private var username = ""
    private var pin = ""

    fun onAction(action: AuthenticationAction) {
        when(action) {
            is AuthenticationAction.OnPinEntered -> {
                pin = action.pin
            }
            is AuthenticationAction.OnUsernameEntered -> {
                username = action.username
            }
        }
    }

    fun saveCredentials() {
        viewModelScope.launch {
            try {
                // Save username and password here
                println("SHAREDVIEMODEL saveCredentials $username $pin")
                insertUserUseCase.execute(
                    User(
                    username = username,
                    pin = pin)
                )
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}

sealed interface AuthenticationAction {
    data class OnUsernameEntered(val username: String) : AuthenticationAction
    data class OnPinEntered(val pin: String) : AuthenticationAction
}

