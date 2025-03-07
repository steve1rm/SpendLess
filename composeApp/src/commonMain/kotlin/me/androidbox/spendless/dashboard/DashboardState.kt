package me.androidbox.spendless.dashboard

import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType

data class DashboardState(
    val newTransaction: Boolean = false,
    val transaction: Transaction = Transaction(),
    val listOfTransactions: List<Transaction> = emptyList()
)

data class Transaction(
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val counterParty: String = "",
    val category: TransactionItems = TransactionItems.FOOD,
    val note: String = "",
    val createAt: Long = 0L,
    val amount: String = "",
)
