package me.androidbox.spendless

import android.content.Context
import android.os.Build
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

actual fun Long.formatMoney(currency: Currency, expensesFormat: ExpensesFormat, thousandsSeparator: ThousandsSeparator, decimalSeparator: DecimalSeparator): String {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        this.groupingSeparator = thousandsSeparator.symbol
        this.decimalSeparator = decimalSeparator.symbol
    }

    val decimalFormat = DecimalFormat("#,##0.##", symbols)
    decimalFormat.isDecimalSeparatorAlwaysShown = false

    val formattedNumber: String = decimalFormat.format(this / 100.0)

    return buildString {
        when(expensesFormat) {
            ExpensesFormat.BRACKET -> append("(")
            ExpensesFormat.NEGATIVE -> append("-")
        }

        append(currency.symbol)
        append(formattedNumber)

        if(expensesFormat == ExpensesFormat.BRACKET) {
            append(")")
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