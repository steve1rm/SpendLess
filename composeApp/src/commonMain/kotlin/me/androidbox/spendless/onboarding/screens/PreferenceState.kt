package me.androidbox.spendless.onboarding.screens

import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator

data class PreferenceState(
    val decimalSeparator: DecimalSeparator = DecimalSeparator.entries.first(),
    val thousandsSeparator: ThousandsSeparator = ThousandsSeparator.entries.first(),
    val currency: Currency = Currency.USD,
    val expensesFormat: ExpensesFormat = ExpensesFormat.NEGATIVE,
    val isEnabled: Boolean = false,
    val money: Long = 0L
)
