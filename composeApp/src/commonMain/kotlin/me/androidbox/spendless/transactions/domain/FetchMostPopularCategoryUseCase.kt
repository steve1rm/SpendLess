package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.transactions.data.Transaction

fun interface FetchMostPopularCategoryUseCase {
    fun execute(): Flow<Result<Transaction>>
}