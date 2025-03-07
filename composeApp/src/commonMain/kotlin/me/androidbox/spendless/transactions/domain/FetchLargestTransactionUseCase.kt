package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.dashboard.Transaction

fun interface FetchLargestTransactionUseCase {
    suspend fun execute(): Flow<Transaction>
}