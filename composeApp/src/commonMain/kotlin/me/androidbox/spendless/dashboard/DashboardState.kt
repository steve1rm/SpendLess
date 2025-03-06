package me.androidbox.spendless.dashboard

import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType

data class DashboardState(
    val newTransaction: Boolean = false,
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val counterParty: String = "",
    val category: Int = TransactionItems.FOOD.ordinal,
    val note: String = "",
    val createAt: Long = 0L,
    val amount: String = "00.00"
)
