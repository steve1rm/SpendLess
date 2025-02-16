package me.androidbox.spendless.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionDao {
    @Query("SELECT * FROM 'transaction'")
    suspend fun getAll(): List<Transaction>

//    @Query("SELECT * FROM transaction WHERE category=")
//    fun getTransactionByCategory(): Flow<List<Transaction>>

//    @Insert
//    suspend fun insertAll(vararg transaction: Transaction)

//    @Delete
//    fun delete(transaction: Transaction)
}