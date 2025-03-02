package me.androidbox.spendless.transactions.domain.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.Repository
import me.androidbox.spendless.transactions.domain.TransactionModel
import me.androidbox.spendless.transactions.domain.populate

class FetchAllTransactionsUseCaseImp(
    private val repository: Repository
) : FetchAllTransactionsUseCase {
    override fun execute(): Flow<List<TransactionModel>> {
        return  populate() //repository.fetchFromRepository()
    }
}