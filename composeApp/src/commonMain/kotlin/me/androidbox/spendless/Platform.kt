package me.androidbox.spendless

import com.liftric.kvault.KVault
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun Long.formatMoney(
    currency: Currency,
    expensesFormat: ExpensesFormat,
    thousandsSeparator: ThousandsSeparator,
    decimalSeparator: DecimalSeparator
): String


interface SpendLessPreference {
    fun setUsername(value: String)
    fun getUsername(): String?

    fun setTimeStamp(value: Long)
    fun getTimeStamp(): Long?
}

expect class SpendLessPreferenceImp : SpendLessPreference {
    override fun setUsername(value: String)
    override fun getUsername(): String?
    override fun setTimeStamp(value: Long)
    override fun getTimeStamp(): Long?
}
