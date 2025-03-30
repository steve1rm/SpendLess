package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.transactions.domain.FetchTotalTransactionAmountUseCase

class FetchTotalTransactionAmountUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : FetchTotalTransactionAmountUseCase {

    override fun execute(): Flow<Double> {
        return spendLessDataSource.getTotalTransactionAmount()
    }
}