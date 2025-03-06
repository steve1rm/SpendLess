package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase

class FetchAllTransactionsUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : FetchAllTransactionsUseCase {
    override fun execute(): Flow<List<Transaction>> {
        return spendLessDataSource.getAllTransaction().map {
            it.map { transactionTable ->
                Transaction(
                    name = transactionTable.name,
                    type = TransactionType.entries[transactionTable.type],
                    counterParty = transactionTable.counterParty,
                    category = TransactionItems.entries[transactionTable.category],
                    note = transactionTable.note,
                    createAt = transactionTable.createAt,
                    amount = transactionTable.amount
                )
            }
        }
    }
}