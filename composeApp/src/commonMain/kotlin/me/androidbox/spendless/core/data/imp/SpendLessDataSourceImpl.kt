package me.androidbox.spendless.core.data.imp

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.data.SpendLessDatabase
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.transactions.data.Transaction
import me.androidbox.spendless.transactions.data.TransactionTable
import kotlin.coroutines.cancellation.CancellationException

class SpendLessDataSourceImpl(
    private val database: SpendLessDatabase
): SpendLessDataSource {
    override suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }

    override suspend fun getUser(username: String): User? {
        return database.userDao().getUser(username)
    }

    override suspend fun validateUser(username: String, pin: String): User? {
        return database.userDao().validateUser(username, pin)
    }

    override suspend fun insertPreference(preferenceTable: PreferenceTable) {
        database.preferenceDao().insertPreference(preferenceTable)
    }

    override fun getPreference(): Flow<PreferenceTable> {
        return database.preferenceDao().getPreference()
    }

    override fun getAllTransaction(): Flow<List<TransactionTable>> {
        return database.transactionDao().getAllTransactions()
    }

    override suspend fun getLargestTransaction(): Flow<Transaction> {
        return database.transactionDao().getLargestTransaction()
            .map { transactionTable ->
                Transaction(
                    id = transactionTable.id,
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

    override suspend fun getTotalSpentPreviousWeek(
        startOfPreviousWeek: Long,
        endOfPreviousWeek: Long
    ): Float {
        return database.transactionDao()
            .getTotalSpentPreviousWeek(startOfPreviousWeek, endOfPreviousWeek)
    }

    override fun getMostPopularCategory(): Flow<Result<Transaction>> {
        return database.transactionDao().getMostPopularCategory()
            .map { transactionTable ->
                Result.success(
                    Transaction(
                        id = transactionTable.id,
                        name = transactionTable.name,
                        type = TransactionType.entries[transactionTable.type],
                        counterParty = transactionTable.counterParty,
                        category = TransactionItems.entries[transactionTable.category],
                        note = transactionTable.note,
                        createAt = transactionTable.createAt,
                        amount = transactionTable.amount
                    )
                )
            }
            .catch { exception ->
                when (exception) {
                    is SQLiteException -> {
                        emit(Result.failure(exception))
                    }
                    is Exception -> {
                        if (exception is CancellationException) {
                            throw CancellationException()
                        }
                        println("RESULT.FAILURE")
                        emit(Result.failure(exception))
                    }
                }
            }
    }

    override suspend fun insertTransaction(transaction: TransactionTable) {
        database.transactionDao().insertTransaction(transaction)
    }
}