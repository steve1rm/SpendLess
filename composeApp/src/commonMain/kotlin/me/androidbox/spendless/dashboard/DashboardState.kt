package me.androidbox.spendless.dashboard

import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType

data class DashboardState(
    val showTransactionBottomSheet: Boolean = false,
    val transaction: Transaction = Transaction(),
    val largestTransaction: Transaction = Transaction(),
    val popularTransaction: Transaction = Transaction(),
    val listOfTransactions: List<Transaction> = emptyList(),
    val totalPreviousSpent: Float = 0.0f
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
