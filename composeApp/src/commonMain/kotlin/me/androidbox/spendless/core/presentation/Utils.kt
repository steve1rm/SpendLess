package me.androidbox.spendless.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.time.Duration

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (event: T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = flow, key2 = onEvent) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { currentEvent ->
                    onEvent(currentEvent)
                }
            }
        }
    }
}

fun showRedBannerForDuration(duration: Duration): Flow<Boolean> {
    return flow {
        emit(true)
        delay(duration)
        emit(false)
    }
}

/** pad a 0 i.e. 04:09 instead of this 4:9 */
fun Long.pad(): String {
    return this.toString().padStart(2, '0')
}

fun getFormattedTime(duration: Duration): String {
    val minutes = duration.inWholeMinutes
    val seconds = duration.inWholeSeconds % 60

    return "${minutes.pad()}:${seconds.pad()}"
}

fun String.formatMoney(currency: Currency, expensesFormat: ExpensesFormat): String {

    // 10,687.45
    // 10 687,45
    // 10.687,45


    return buildString {
        if(expensesFormat == ExpensesFormat.BRACKET) {
            append("(")
        }
        else {
            append("-")
        }
        append(currency.symbol)
        append(" ")
        append(this@formatMoney)
        if(expensesFormat == ExpensesFormat.BRACKET) {
            append(")")
        }
    }
}