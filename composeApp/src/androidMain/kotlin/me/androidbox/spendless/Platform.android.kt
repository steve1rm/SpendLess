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



// Assuming these enums/classes exist:
// enum class ExpensesFormat { NORMAL, NEGATIVE, BRACKET }
// data class Currency(val symbol: String)
// data class ThousandsSeparator(val symbol: Char)
// data class DecimalSeparator(val symbol: Char)

actual fun Double.formatMoney(
    currency: Currency,
    expensesFormat: ExpensesFormat,
    thousandsSeparator: ThousandsSeparator,
    decimalSeparator: DecimalSeparator
): AnnotatedString {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        this.groupingSeparator = thousandsSeparator.symbol
        this.decimalSeparator = decimalSeparator.symbol
        // It's often better to let DecimalFormat handle the currency symbol
        // based on locale or pattern, but we can force it if needed.
        // this.currencySymbol = currency.symbol // Optional: if pattern uses '¤'
    }

    // Use a pattern that forces two decimal places and includes grouping separators.
    // Include the currency symbol in the pattern for better spacing/locale handling.
    // Use '\u00A4' for the generic currency symbol placeholder if DecimalFormatSymbols.currencySymbol is set,
    // or hardcode currency.symbol if needed. Let's hardcode for simplicity here.
    // Note: Placing symbol might differ by locale convention; this forces it at the start.
    val pattern = "'${currency.symbol}'#,##0.00" // Example: $1,234.56 or €1.234,56
    val decimalFormat = DecimalFormat(pattern, symbols)

    val isNegative = this < 0
    val amountToFormat = kotlin.math.abs(this)

    // Format the absolute numeric value
    val formattedNumberString = decimalFormat.format(amountToFormat)

    return buildAnnotatedString {
        // Determine color based on negativity
        val numberColor = if (isNegative) Color.Red else Color.Unspecified // Use Color.Unspecified for default/theme color

        // Handle negative sign/brackets based on preference
        if (isNegative) {
            when (expensesFormat) {
                ExpensesFormat.BRACKET -> withStyle(style = SpanStyle(color = numberColor)) { append("(") }
                ExpensesFormat.NEGATIVE -> {
                    // Check if the format already added a minus (depends on locale/pattern)
                    // If not, add it. Assuming our pattern doesn't add it.
                    if (!formattedNumberString.startsWith("-")) {
                        withStyle(style = SpanStyle(color = numberColor)) { append("-") }
                    }
                }
                else -> {} // Handle other formats or default behavior
            }
        }

        // Append the formatted number (which now includes currency symbol and decimals)
        // Apply color to the whole thing
        withStyle(style = SpanStyle(color = numberColor)) {
            // If we added our own "-", remove any potential "-" from the formatted string itself
            append(formattedNumberString.removePrefix("-"))
        }

        // Add closing bracket if needed
        if (isNegative && expensesFormat == ExpensesFormat.BRACKET) {
            withStyle(style = SpanStyle(color = numberColor)) { append(")") }
        }
    }
}
/*

actual fun Double.formatMoney0ld(currency: Currency, expensesFormat: ExpensesFormat, thousandsSeparator: ThousandsSeparator, decimalSeparator: DecimalSeparator): AnnotatedString {
    val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
        this.groupingSeparator = thousandsSeparator.symbol
        this.decimalSeparator = decimalSeparator.symbol
    }
    */
/** Check if the amount is negative *//*

    val isNegative = this < 0
    */
/** Display the money without the negative *//*

    val amount = kotlin.math.abs(this)

    val decimalFormat = DecimalFormat("#,##0.##", symbols)
    decimalFormat.isDecimalSeparatorAlwaysShown = false

    */
/** Divide by 100 to show the decimal number *//*

 */
/*   val formattedNumber = if(amount > 90000) {
        decimalFormat.format(amount / 100.0)
    }
    else {
        decimalFormat.format(amount)
    }*//*


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
        append(amount.toString())

        if(expensesFormat == ExpensesFormat.BRACKET) {
            if(isNegative) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(")")
                }
            }
        }
    }
}
*/

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