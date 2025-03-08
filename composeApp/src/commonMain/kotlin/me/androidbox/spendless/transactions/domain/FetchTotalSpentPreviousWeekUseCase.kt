package me.androidbox.spendless.transactions.domain

fun interface FetchTotalSpentPreviousWeekUseCase {
    suspend fun execute(): Float
}