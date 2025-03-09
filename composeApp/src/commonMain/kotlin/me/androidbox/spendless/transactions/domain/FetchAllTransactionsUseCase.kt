package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.dashboard.AllTransactions

fun interface FetchAllTransactionsUseCase {
    fun execute(): Flow<List<AllTransactions>>
}
