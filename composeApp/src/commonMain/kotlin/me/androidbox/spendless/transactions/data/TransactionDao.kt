package me.androidbox.spendless.transactions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: TransactionTable)

    @Query("SELECT * FROM transactionTable")
    fun getAllTransactions(): Flow<List<TransactionTable>>

    @Query("SELECT * FROM transactionTable WHERE amount = (SELECT MAX(amount) FROM transactionTable)")
    fun getLargestTransaction(): Flow<TransactionTable>

/*
    @Query("SELECT MAX(amount) FROM transactionTable")
    suspend fun getLargestTransaction(): Flow<TransactionTable>
*/

//    @Query("SELECT * FROM transaction WHERE category=")
//    fun getTransactionByCategory(): Flow<List<Transaction>>

//    @Delete
//    fun delete(transaction: Transaction)
}