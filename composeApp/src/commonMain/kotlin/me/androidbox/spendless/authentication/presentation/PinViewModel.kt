package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.presentation.CreatePinEvents.*

class PinViewModel : ViewModel() {

    private val _createPinState = MutableStateFlow<CreatePinState>(CreatePinState())
    val createPinState = _createPinState.asStateFlow()

    private val _pinChannel = Channel<CreatePinEvents>()
    val pinChannel = _pinChannel.consumeAsFlow()

    fun onAction(action: CreatePinActions) {
        when(action) {
            is CreatePinActions.OnPinNumberEntered -> {
                when(createPinState.value.pinMode) {
                    PinMode.CREATE -> {
                        if(createPinState.value.createPinList.count() < 5) {
                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    createPinList = createPinState.createPinList + action.pinNumber
                                )
                            }

                            println("PIN Entered ${createPinState.value.createPinList}")

                            if(createPinState.value.createPinList.count() == 5) {
                                println("Change mode to repeat ${createPinState.value.createPinList}")
                                _createPinState.update { createPinState ->
                                    createPinState.copy(
                                        secretPin = createPinState.createPinList,
                                        createPinList = emptyList(),
                                        pinMode = PinMode.REPEAT
                                    )
                                }
                            }
                        }
                    }

                    PinMode.REPEAT -> {
                        if (createPinState.value.createPinList.count() < 5) {
                            println("Secret PIN ${createPinState.value.secretPin}")
                            println("Entered repeated PIN ${action.pinNumber}")

                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    createPinList = createPinState.createPinList + action.pinNumber
                                )
                            }

                            if(createPinState.value.createPinList.count() == 5) {
                                val hasValidPinNumbers = pinEntryValid(createPinState.value.secretPin, createPinState.value.createPinList)
                                println("Valid repeated PIN $hasValidPinNumbers")

                                _createPinState.update { createPinState ->
                                    createPinState.copy(
                                        createPinList = emptyList(),
                                    )
                                }

                                viewModelScope.launch {
                                    _pinChannel.send(PinEntryEvent(isValid = hasValidPinNumbers))
                                }
                            }
                        }
                    }
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

            is CreatePinActions.ShouldShowRedBanner -> {
                _createPinState.update { createPinState ->
                    createPinState.copy(
                        isValidPin = action.showBanner
                    )
                }
            }
        }
    }
}

fun pinEntryValid(secretPin: List<KeyButtons>, repeatedPin: List<KeyButtons>): Boolean {
    var hasEnteredValidPin = true

    secretPin.forEachIndexed { index, pinNumber ->
        if(pinNumber != repeatedPin[index]) {
            hasEnteredValidPin = false
            return@forEachIndexed
        }
    }

    return hasEnteredValidPin
}

private fun PinViewModel.isValidPinEntered(pinNumber: KeyButtons): Boolean {
    val repeatedPinList = createPinState.value.createPinList + pinNumber

    val isValid =
        createPinState.value.secretPin[repeatedPinList.count() - 1] == repeatedPinList[repeatedPinList.count() - 1]

    return isValid
}