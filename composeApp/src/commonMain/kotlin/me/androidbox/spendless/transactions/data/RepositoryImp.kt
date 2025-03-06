package me.androidbox.spendless.transactions.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.transactions.domain.Repository
import me.androidbox.spendless.transactions.domain.TransactionModel

class RepositoryImp : Repository {
    override fun fetchFromRepository(): Flow<List<Transaction>> {
        TODO()
    }
}