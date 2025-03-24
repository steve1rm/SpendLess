package me.androidbox.spendless

import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.liftric.kvault.KVault
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun Long.formatMoney(currency: Currency, expensesFormat: ExpensesFormat, thousandsSeparator: ThousandsSeparator, decimalSeparator: DecimalSeparator): AnnotatedString {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        this.groupingSeparator = thousandsSeparator.symbol
        this.decimalSeparator = decimalSeparator.symbol
    }
    /** Check if the amount is negative */
    val isNegative = this < 0
    /** Display the money without the negative */
    val amount = kotlin.math.abs(this)

    val decimalFormat = DecimalFormat("#,##0.##", symbols)
    decimalFormat.isDecimalSeparatorAlwaysShown = false

    /** Divide by 100 to show the decimal number */
    val formattedNumber = if(amount > 90000) {
        decimalFormat.format(amount / 100.0)
    }
    else {
        decimalFormat.format(amount)
    }

    return buildAnnotatedString {
        if(isNegative) {
            when (expensesFormat) {
                ExpensesFormat.BRACKET -> {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("(")
                    }
                }

                ExpensesFormat.NEGATIVE -> {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("-")
                    }
                }
            }
        }

        append(currency.symbol)
        append(formattedNumber)

        if(expensesFormat == ExpensesFormat.BRACKET) {
            if(isNegative) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(")")
                }
            }
        }
    }
}

actual class SpendLessPreferenceImp(context: Context) : SpendLessPreference {

    private val store = KVault(context, "SpendLess")

    actual override suspend fun setUsername(value: String) {
        store.set("USERNAME", value)
    }

    actual override suspend fun getUsername(): String? {
        return store.string("USERNAME")
    }

    actual override suspend fun setTimeStamp(value: Long) {
        store.set("TIMESTAMP", value)
    }

    actual override suspend fun getTimeStamp(): Long? {
        return store.long("TIMESTAMP")
    }

    actual override suspend fun clearAll() {
        store.clear()
    }
}