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
                if(_createPinState.value.createPinList.count() < 5) {
                    _createPinState.update { createPinState ->
                        createPinState.copy(
                            createPinList = createPinState.createPinList + action.pinNumber
                        )
                    }
                }
            }
            CreatePinActions.OnDeletePressed -> {
                _createPinState.update { createPinState ->
                    createPinState.copy(
                        createPinList = createPinState.createPinList.dropLast(1)
                    )
                }
            }
        }
    }
}