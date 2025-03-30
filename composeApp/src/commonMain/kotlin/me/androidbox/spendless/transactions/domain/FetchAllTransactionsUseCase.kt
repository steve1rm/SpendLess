package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.transactions.data.AllTransactions

fun interface FetchAllTransactionsUseCase {
    fun execute(): Flow<List<AllTransactions>>
}
