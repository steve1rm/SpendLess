package me.androidbox.spendless.domain

import me.androidbox.spendless.data.Transaction

interface CreateTransactionUseCase {
    suspend fun execute(transaction: Transaction): Result<Unit>
}
