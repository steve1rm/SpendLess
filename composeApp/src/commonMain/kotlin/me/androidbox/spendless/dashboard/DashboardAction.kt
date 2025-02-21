package me.androidbox.spendless.dashboard

import me.androidbox.spendless.core.presentation.TransactionType

sealed interface DashboardAction {
    data class OpenNewTransaction(val shouldOpen: Boolean) : DashboardAction
    data object OpenSettings : DashboardAction
    data object OnCreateClicked : DashboardAction
    data class OnTransactionTypeClicked(val transactionType: TransactionType) : DashboardAction
    data class OnTransactionNameEntered(val name: String) : DashboardAction
    data class OnTransactionAmountEntered(val amount: String) : DashboardAction
    data object OnShowAllClicked : DashboardAction
}