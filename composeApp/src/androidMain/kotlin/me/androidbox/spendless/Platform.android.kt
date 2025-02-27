package me.androidbox.spendless

import android.os.Build
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import me.androidbox.spendless.core.presentation.formatMoney
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

    val decimalFormat = DecimalFormat("##,###.##", symbols)
    decimalFormat.isDecimalSeparatorAlwaysShown = false

  //  val number = 6347238245
    val formattedNumber: String = decimalFormat.format(this / 100.0)

    return buildString {
        if(expensesFormat == ExpensesFormat.BRACKET) {
            append("(")
        }
        else {
            append("-")
        }
        append(currency.symbol)
        append(" ")
        append(formattedNumber)
        if(expensesFormat == ExpensesFormat.BRACKET) {
            append(")")
        }
    }

    println(formattedNumber)

    return formattedNumber
}