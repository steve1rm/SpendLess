package me.androidbox.spendless.core.data.imp

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.core.data.SpendLessDatabase
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.settings.data.PreferenceTable

class SpendLessDataSourceImpl(
    private val database: SpendLessDatabase
): SpendLessDataSource {
    override suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }

    override suspend fun getUser(username: String): User? {
        return database.userDao().getUser(username)
    }

    override suspend fun insertPreference(preferenceTable: PreferenceTable) {
        database.preferenceDao().insertPreference(preferenceTable)
    }

    override suspend fun getPreference(): PreferenceTable {
        return database.preferenceDao().getPreference()
    }

    override fun getAllTransaction(): Flow<List<TransactionTable>> {
        return database.transactionDao().getAll()
    }

    override suspend fun insertTransaction(transaction: TransactionTable) {
        database.transactionDao().insertTransaction(transaction)
    }
}