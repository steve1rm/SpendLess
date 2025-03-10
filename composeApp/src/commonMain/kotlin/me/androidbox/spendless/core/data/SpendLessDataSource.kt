package me.androidbox.spendless.core.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.transactions.data.EncryptedTransactionTable

interface SpendLessDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUser(username: String): User?
    suspend fun validateUser(username: String, pin: String): User?

    suspend fun insertPreference(preferenceTable: PreferenceTable)
    suspend fun getPreference(): PreferenceTable

    suspend fun insertTransaction(transaction: TransactionTable)
    suspend fun insertEncryptedTransaction(transactionToEncrypt: EncryptedTransactionTable)

    fun getAllTransaction(): Flow<List<TransactionTable>>
    suspend fun getLargestTransaction(): Flow<Transaction>
    suspend fun getTotalSpentPreviousWeek(startOfPreviousWeek: Long, endOfPreviousWeek: Long): Float
    fun getMostPopularCategory(): Flow<Transaction>

//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}