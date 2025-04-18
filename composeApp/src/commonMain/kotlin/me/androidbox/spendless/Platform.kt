package me.androidbox.spendless

import androidx.compose.ui.text.AnnotatedString
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun Double.formatMoney(
    currency: Currency,
    expensesFormat: ExpensesFormat,
    thousandsSeparator: ThousandsSeparator,
    decimalSeparator: DecimalSeparator
): AnnotatedString


interface SpendLessPreference {
    suspend fun setUsername(value: String)
    suspend fun getUsername(): String?

    suspend fun setTimeStamp(value: Long)
    suspend fun getTimeStamp(): Long?

    suspend fun clearAll()
}

expect class SpendLessPreferenceImp : SpendLessPreference {
    override suspend fun setUsername(value: String)
    override suspend fun getUsername(): String?
    override suspend fun setTimeStamp(value: Long)
    override suspend fun getTimeStamp(): Long?
    override suspend fun clearAll()
}
