package me.androidbox.spendless.authentication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.spendless.authentication.presentation.CreatePinEvent.*
import me.androidbox.spendless.core.presentation.Authentication
import me.androidbox.spendless.core.presentation.KeyButtons
import me.androidbox.spendless.core.presentation.PinMode
import me.androidbox.spendless.core.presentation.countDownTimer
import me.androidbox.spendless.core.presentation.showRedBannerForDuration
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class PinViewModel : ViewModel() {

    private val _createPinState = MutableStateFlow<CreatePinState>(CreatePinState())
    val createPinState = _createPinState.asStateFlow()

    private val _pinChannel = Channel<CreatePinEvent>()
    val pinChannel = _pinChannel.consumeAsFlow()

    private var tryAttempts = 0

    /** Maybe add this to the core presentation */
    private fun disableKeyPadForDuration(duration: Duration = 30.seconds) {
        countDownTimer(duration)
            .onStart {
                println("Start Countdown Flow")
                _createPinState.update { createPinState ->
                    createPinState.copy(
                        enableKeyPad = false,
                        authentication = Authentication.AUTHENTICATION_FAILED)
                }
            }
            .onEach { duration ->
                _createPinState.update { createPinState ->
                    createPinState.copy(
                        countdownTime = duration
                    )
                }
            }
            .onCompletion {
                println("End Countdown Flow")
                _createPinState.update { createPinState ->
                    createPinState.copy(enableKeyPad = true,
                        authentication = Authentication.AUTHENTICATION_PROMPT)
                }
                tryAttempts = 0
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: CreatePinActions) {
        when(action) {
            is CreatePinActions.OnPinNumberEntered -> {
                when(createPinState.value.pinMode) {
                    PinMode.CREATE -> {
                        if(createPinState.value.pinInputList.count() < 5) {
                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    pinInputList = createPinState.pinInputList + action.pinNumber.key
                                )
                            }

                        //    println("PIN Entered ${createPinState.value.pinInputList}")

                            if(createPinState.value.pinInputList.count() == 5) {
                                println("Change mode to repeat ${createPinState.value.pinInputList}")
                                _createPinState.update { createPinState ->
                                    createPinState.copy(
                                        secretPin = createPinState.pinInputList,
                                        pinInputList = emptyList(),
                                        pinMode = PinMode.REPEAT
                                    )
                                }
                            }
                        }
                    }

                    PinMode.REPEAT -> {
                        if (createPinState.value.pinInputList.count() < 5) {
                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    pinInputList = createPinState.pinInputList + action.pinNumber.key
                                )
                            }

                            if(createPinState.value.pinInputList.count() == 5) {
                                val hasValidPinNumbers = pinEntryValid(createPinState.value.secretPin, createPinState.value.pinInputList)

                                viewModelScope.launch {
                                    _pinChannel.send(PinEntryEvent(isValid = hasValidPinNumbers, createPinState.value.pinInputList.joinToString("")))
                                }

                                _createPinState.update { createPinState ->
                                    createPinState.copy(
                                        pinInputList = emptyList(),
                                    )
                                }
                            }
                        }
                    }

                    PinMode.AUTHENTICATION -> {
                        _createPinState.update { createPinState ->
                            createPinState.copy(
                                authentication = Authentication.AUTHENTICATION_PROMPT)
                        }

                        if (createPinState.value.pinInputList.count() < 5) {
                            /** Get this from the encrypted shared preferences */
                            _createPinState.update {
                                it.copy(secretPin = listOf(KeyButtons.ONE.key, KeyButtons.TWO.key, KeyButtons.THREE.key, KeyButtons.FOUR.key, KeyButtons.FIVE.key))
                            }

                            println("Authentication Secret PIN ${createPinState.value.secretPin}")
                            println("Authentication Entered repeated PIN ${action.pinNumber}")

                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    pinInputList = createPinState.pinInputList + action.pinNumber.key
                                )
                            }

                            if(createPinState.value.pinInputList.count() == 5) {
                                val hasValidPinNumbers = pinEntryValid(createPinState.value.secretPin, createPinState.value.pinInputList)

                                if(!hasValidPinNumbers) {
                                    tryAttempts += 1
                                    showRedBannerForDuration(2.seconds)
                                        .onEach { showBanner ->
                                            _createPinState.update { createPinState ->
                                                createPinState.copy(shouldShowRedBanner = showBanner)
                                            }
                                        }.launchIn(viewModelScope)
                                }
                                println("Authentication Valid repeated [ $tryAttempts ] PIN $hasValidPinNumbers")

                                if(tryAttempts == 3) {
                                    disableKeyPadForDuration()
                                }

                                _createPinState.update { createPinState ->
                                    createPinState.copy(
                                        pinInputList = emptyList(),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            CreatePinActions.OnDeletePressed -> {
                if(createPinState.value.pinInputList.isNotEmpty()) {
                    _createPinState.update { createPinState ->
                        createPinState.copy(
                            pinInputList = createPinState.pinInputList.dropLast(1)
                        )
                    }
                    println("PIN Entered ${createPinState.value.pinInputList}")
                }
            }

            is CreatePinActions.ShouldShowRedBanner -> {
                showRedBannerForDuration(2.seconds)
                    .onEach { shouldShow ->
                        _createPinState.update { createPinState ->
                            createPinState.copy(
                                shouldShowRedBanner = shouldShow
                            )
                        }
                    }.launchIn(viewModelScope)
            }

            is CreatePinActions.ShouldUpdateMode -> {
                _createPinState.update { createPinState ->
                    createPinState.copy(
                        pinMode = action.pinMode
                    )
                }
            }

            CreatePinActions.OnBackPressed -> {
                /** no-op */
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared PIN_VIEWMODEL")
    }
}

fun pinEntryValid(secretPin: List<String>, repeatedPin: List<String>): Boolean {
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
    val repeatedPinList = createPinState.value.pinInputList + pinNumber

    val isValid =
        createPinState.value.secretPin[repeatedPinList.count() - 1] == repeatedPinList[repeatedPinList.count() - 1]

    return isValid
}