package me.androidbox.spendless.transactions

import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.transactions.domain.TransactionModel

data class TransactionState(
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val amount: String = "00.00",
    val listOfTransactions: List<TransactionModel> = emptyList(),
    val isLoading: Boolean = false
)
