package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.Transaction
import me.androidbox.spendless.transactions.domain.CreateTransactionUseCase


class CreateTransactionUseCaseImp(
    private val dataSource: SpendLessDataSource
) : CreateTransactionUseCase {
    override suspend fun execute(transaction: Transaction): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                dataSource.insertTransaction(transaction)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}