package me.androidbox.spendless.data

import me.androidbox.spendless.data.Transaction

interface SpendLessDataSource {

    suspend fun getAllTransaction(): List<Transaction>
//    suspend fun getTransactionByCategory(title: String): List<Transaction>
//    suspend fun insertTransaction(topic: Transaction)
//    fun getAllJournal(): Flow<List<EchoJournalUI>>
}