package me.androidbox.spendless.transactions.data

import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType

data class Transaction(
    val id: Int = 0,
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val counterParty: String = "",
    val category: TransactionItems = TransactionItems.FOOD,
    val note: String = "",
    val createAt: Long = 0L,
    val amount: String = "",
)