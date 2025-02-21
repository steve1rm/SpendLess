package me.androidbox.spendless.transactions.domain

import me.androidbox.spendless.core.presentation.TransactionItems

data class TransactionModel(
    val title: String,
    val description: TransactionItems,
    val note: String,
    val amount: Float,
    val date: Long
)
