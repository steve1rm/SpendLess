package me.androidbox.spendless.core.presentation

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun countDownTimer(initialTime: Duration): Flow<Duration> {
    return flow {
        var timeRemaining = initialTime

        while (timeRemaining > Duration.ZERO) {
            emit(timeRemaining)
            delay(1.seconds)
            timeRemaining -= 1.seconds
        }

        emit(Duration.ZERO)
    }
}