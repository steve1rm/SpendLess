package me.androidbox.spendless.transactions

import me.androidbox.spendless.core.presentation.TransactionType

data class TransactionState(
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val amount: String = "00.00"
)
