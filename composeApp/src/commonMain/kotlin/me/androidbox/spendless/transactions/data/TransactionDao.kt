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
    fun getAll(): Flow<List<TransactionTable>>

//    @Query("SELECT * FROM transaction WHERE category=")
//    fun getTransactionByCategory(): Flow<List<Transaction>>

//    @Delete
//    fun delete(transaction: Transaction)
}