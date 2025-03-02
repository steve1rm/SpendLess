package me.androidbox.spendless

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

expect fun generatePinDigest(
    username: String,
    pin: String
):String