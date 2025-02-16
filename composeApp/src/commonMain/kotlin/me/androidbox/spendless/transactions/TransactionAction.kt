package me.androidbox.spendless.transactions

import me.androidbox.spendless.core.presentation.TransactionType

sealed interface TransactionAction {
    data object OnCreateClicked : TransactionAction
    data class OnTransactionTypeClicked(val transactionType: TransactionType) : TransactionAction
    data class OnTransactionNameEntered(val name: String) : TransactionAction
    data class OnTransactionAmountEntered(val amount: String) : TransactionAction
}