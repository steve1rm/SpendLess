package me.androidbox.spendless.onboarding.screens

import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.ThousandsSeparator

sealed interface PreferenceAction {
    data class OnDecimalSeparatorSelected(val decimalSeparator: DecimalSeparator) : PreferenceAction
    data class OnThousandsSeparatorSelected(val thousandsSeparator: ThousandsSeparator) : PreferenceAction
    data class OnExpensesFormat(val expensesFormat: ExpensesFormat) : PreferenceAction
    data class OnCurrency(val currency: Currency) : PreferenceAction
}
