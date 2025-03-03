package me.androidbox.spendless.transactions.domain

import me.androidbox.spendless.transactions.data.TransactionTable

interface InsertTransactionUseCase {
    suspend fun execute(transaction: TransactionTable)
}
