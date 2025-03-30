package me.androidbox.spendless.transactions.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.androidbox.spendless.transactions.domain.Repository

class RepositoryImp : Repository {
    override fun fetchFromRepository(): Flow<List<Transaction>> {
        return emptyFlow<List<Transaction>>()
    }
}