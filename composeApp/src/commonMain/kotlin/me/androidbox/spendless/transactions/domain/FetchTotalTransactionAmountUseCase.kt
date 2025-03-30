package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow

fun interface FetchTotalTransactionAmountUseCase {
    fun execute(): Flow<Double>
}
