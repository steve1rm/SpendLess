package me.androidbox.spendless.core.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.Session
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.settings.data.PreferenceTable

interface SpendLessDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUser(username: String): User?
    suspend fun validateUser(username: String, pin: String): User?

    suspend fun createSession(session: Session)
//    suspend fun getSession(userId: Int)

    suspend fun insertPreference(preferenceTable: PreferenceTable)
    suspend fun getPreference(): PreferenceTable

    suspend fun insertTransaction(transaction: TransactionTable)
    fun getAllTransaction(): Flow<List<TransactionTable>>

//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}