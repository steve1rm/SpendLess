package me.androidbox.spendless

import androidx.compose.ui.text.AnnotatedString
import com.liftric.kvault.KVault
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator

actual fun Double.formatMoney(
    currency: Currency,
    expensesFormat: ExpensesFormat,
    thousandsSeparator: ThousandsSeparator,
    decimalSeparator: DecimalSeparator
): AnnotatedString {
    TODO("Not yet implemented")
}

actual class SpendLessPreferenceImp : SpendLessPreference {
    actual override fun setUsername(key: String, value: String) {
        TODO("Not yet implemented")
    }

    actual override fun getUsername(key: String): String? {
        TODO("Not yet implemented")
    }

    actual override fun setTimeStamp(key: String, value: Long) {
        TODO("Not yet implemented")
    }

    actual override fun getTimeStamp(key: String): Long? {
        TODO("Not yet implemented")
    }

    actual override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}