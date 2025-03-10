package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.dashboard.Transaction

fun interface FetchMostPopularCategoryUseCase {
    fun execute(): Flow<Result<Transaction>>
}