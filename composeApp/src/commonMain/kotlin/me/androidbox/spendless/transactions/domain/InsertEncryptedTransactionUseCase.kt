package me.androidbox.spendless.transactions.domain

import me.androidbox.spendless.transactions.data.TransactionTable

interface InsertEncryptedTransactionUseCase {
    suspend fun execute(transactionToEncrypt: TransactionTable)
}
