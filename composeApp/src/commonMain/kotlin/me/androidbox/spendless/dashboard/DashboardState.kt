package me.androidbox.spendless.dashboard

import me.androidbox.spendless.core.presentation.TransactionType

data class DashboardState(
    val newTransaction: Boolean = false,
    val name: String = "",
    val type: TransactionType = TransactionType.RECEIVER,
    val amount: String = "00.00"
)
