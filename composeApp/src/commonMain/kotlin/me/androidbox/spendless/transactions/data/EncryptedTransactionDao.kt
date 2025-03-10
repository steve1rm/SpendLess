package me.androidbox.spendless.transactions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EncryptedTransactionDao {

    @Insert
    suspend fun insertEncryptedTransaction(encryptedTransaction: EncryptedTransactionTable)

    @Query("SELECT * FROM encryptedTransactionTable")
    fun getAllEncryptedTransactions(): Flow<List<EncryptedTransactionTable>>
}