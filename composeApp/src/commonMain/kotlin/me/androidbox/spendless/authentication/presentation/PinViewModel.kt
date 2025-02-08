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
import me.androidbox.spendless.authentication.presentation.CreatePinEvents.*
import me.androidbox.spendless.core.presentation.countDownTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class PinViewModel : ViewModel() {

    private val _createPinState = MutableStateFlow<CreatePinState>(CreatePinState())
    val createPinState = _createPinState.asStateFlow()

    private val _pinChannel = Channel<CreatePinEvents>()
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

                    PinMode.AUTHENTICATION -> {
                        _createPinState.update { createPinState ->
                            createPinState.copy(
                                authentication = Authentication.AUTHENTICATION_PROMPT)
                        }

                        if (createPinState.value.createPinList.count() < 5) {
                            /** Get this from the encrypted shared preferences */
                            _createPinState.update {
                                it.copy(secretPin = listOf(KeyButtons.ONE, KeyButtons.TWO, KeyButtons.THREE, KeyButtons.FOUR, KeyButtons.FIVE))
                            }

                            println("Authentication Secret PIN ${createPinState.value.secretPin}")
                            println("Authentication Entered repeated PIN ${action.pinNumber}")

                            _createPinState.update { createPinState ->
                                createPinState.copy(
                                    createPinList = createPinState.createPinList + action.pinNumber
                                )
                            }

                            if(createPinState.value.createPinList.count() == 5) {
                                val hasValidPinNumbers = pinEntryValid(createPinState.value.secretPin, createPinState.value.createPinList)

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
                                        createPinList = emptyList(),
                                    )
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
        }
    }

    private fun showRedBannerForDuration(duration: Duration): Flow<Boolean> {
        return flow {
            emit(true)
            delay(duration)
            emit(false)
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