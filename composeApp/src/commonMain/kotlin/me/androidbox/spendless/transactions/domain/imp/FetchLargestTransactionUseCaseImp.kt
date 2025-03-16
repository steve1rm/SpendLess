package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.domain.FetchLargestTransactionUseCase

class FetchLargestTransactionUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : FetchLargestTransactionUseCase {
    override suspend fun execute(): Flow<Transaction> {
        return spendLessDataSource.getLargestTransaction()
    }
}