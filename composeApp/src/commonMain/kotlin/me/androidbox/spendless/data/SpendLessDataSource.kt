package me.androidbox.spendless.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.settings.data.PreferenceTable

interface SpendLessDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUser(username: String): User

    suspend fun insertPreference(preferenceTable: PreferenceTable)
    suspend fun getPreference(): PreferenceTable

    suspend fun insertTransaction(transaction: Transaction)
    fun getAllTransaction(): Flow<List<Transaction>>

//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}