package me.androidbox.spendless.core.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.transactions.data.Transaction

interface SpendLessDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUser(username: String): User?
    suspend fun validateUser(username: String, pin: String): User?

    suspend fun insertPreference(preferenceTable: PreferenceTable)
    fun getPreference(): Flow<PreferenceTable>

    suspend fun insertTransaction(transaction: TransactionTable)
    fun getAllTransaction(): Flow<List<TransactionTable>>
    suspend fun getLargestTransaction(): Flow<Transaction>
    fun getTotalTransactionAmount(): Flow<Long>

    suspend fun getTotalSpentPreviousWeek(startOfPreviousWeek: Long, endOfPreviousWeek: Long): Float
    fun getMostPopularCategory(): Flow<Result<Transaction>>

//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}