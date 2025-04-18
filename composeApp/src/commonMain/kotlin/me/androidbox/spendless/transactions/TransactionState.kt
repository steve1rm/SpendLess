package me.androidbox.spendless.transactions

import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.transactions.data.Transaction

data class TransactionState(
    val name: String = "",
    val type: String = TransactionType.RECEIVER.typeName,
    val amount: String = "00.00",
    val listOfTransactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false
)
