package me.androidbox.spendless.transactions.domain

import kotlinx.coroutines.flow.Flow

class FetchAllTransactionsUseCaseImp(
    private val repository: Repository
) : FetchAllTransactionsUseCase {
    override fun execute(): Flow<List<TransactionModel>> {
        return  populate() //repository.fetchFromRepository()
    }
}