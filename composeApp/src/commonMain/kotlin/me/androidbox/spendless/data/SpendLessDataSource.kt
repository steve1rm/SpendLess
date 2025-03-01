package me.androidbox.spendless.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.spendless.data.Transaction

interface SpendLessDataSource {
    suspend fun insertUser(user: User)

    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getAllTransaction(): Flow<List<Transaction>>
//    suspend fun getTransactionByCategory(title: String): List<Transaction>
<<<<<<< HEAD
//    fun getAllJournal(): Flow<List<SpendLessUI>>
=======
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
>>>>>>> 1f1e1d14dbd8567ee7b1093a285e279d5c2cb016
}