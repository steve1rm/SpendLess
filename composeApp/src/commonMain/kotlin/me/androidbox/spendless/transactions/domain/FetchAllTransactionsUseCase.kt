package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.dashboard.TransactionHeader
import kotlin.random.Random
import kotlin.time.Duration.Companion.days

fun interface FetchAllTransactionsUseCase {
    fun execute(): Flow<List<TransactionHeader>>
}
