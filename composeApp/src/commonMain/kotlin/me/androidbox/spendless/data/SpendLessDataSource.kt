package me.androidbox.spendless.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.data.Transaction

interface SpendLessDataSource {
    suspend fun insertUser(user: User)

    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getAllTransaction(): Flow<List<Transaction>>
//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}