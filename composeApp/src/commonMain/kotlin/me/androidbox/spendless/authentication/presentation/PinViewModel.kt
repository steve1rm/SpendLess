package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PinViewModel : ViewModel() {

    private val _createPinState = MutableStateFlow<CreatePinState>(CreatePinState())
    val createPinState = _createPinState.asStateFlow()

    fun onAction(action: CreatePinActions) {
        when(action) {
            is CreatePinActions.OnPinNumberEntered -> {
                if(createPinState.value.createPinList.count() < 5) {
                    _createPinState.update { createPinState ->
                        createPinState.copy(
                            createPinList = createPinState.createPinList + action.pinNumber
                        )
                    }
                    println("PIN Entered ${createPinState.value.createPinList}")
                }
            }

            CreatePinActions.OnDeletePressed -> {
                if(createPinState.value.createPinList.isNotEmpty()) {
                    _createPinState.update { createPinState ->
                        createPinState.copy(
                            createPinList = createPinState.createPinList.dropLast(1)
                        )
                    }
                    println("PIN Entered ${createPinState.value.createPinList}")
                }
            }
        }
    }
}