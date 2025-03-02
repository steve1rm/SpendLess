package me.androidbox.spendless.transactions.domain

import me.androidbox.spendless.data.Transaction

interface CreateTransactionUseCase {
    suspend fun execute(transaction: Transaction): Result<Unit>
}
