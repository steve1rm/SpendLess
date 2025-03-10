package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.transactions.domain.FetchMostPopularCategoryUseCase

class FetchMostPopularCategoryUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
): FetchMostPopularCategoryUseCase {
    override fun execute(): Flow<Result<Transaction>> {
        return spendLessDataSource.getMostPopularCategory()
    }
}