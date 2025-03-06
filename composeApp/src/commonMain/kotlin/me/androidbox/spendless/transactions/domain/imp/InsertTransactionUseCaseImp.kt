package me.androidbox.spendless.transactions.domain.imp

import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase


class InsertTransactionUseCaseImp(
    private val dataSource: SpendLessDataSource
) : InsertTransactionUseCase {
    override suspend fun execute(transaction: TransactionTable) {
        dataSource.insertTransaction(transaction)
    }
}