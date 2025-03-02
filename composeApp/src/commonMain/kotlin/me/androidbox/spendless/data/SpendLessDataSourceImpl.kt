package me.androidbox.spendless.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.core.data.SpendLessDatabase

class SpendLessDataSourceImpl(
    private val database: SpendLessDatabase
): SpendLessDataSource {
    override suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }

    override suspend fun getUser(username: String): User {
        return database.userDao().getUser(username)
    }

    override fun getAllTransaction(): Flow<List<Transaction>> {
        return database.transactionDao().getAll()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        database.transactionDao().insertTransaction()
    }
}