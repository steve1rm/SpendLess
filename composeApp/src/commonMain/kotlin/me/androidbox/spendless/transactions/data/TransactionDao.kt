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

    @Query("""
        SELECT SUM(CAST(amount AS REAL))
        FROM transactionTable
        WHERE createAt BETWEEN :startOfPreviousWeek AND :endOfPreviousWeek
    """)
    suspend fun getTotalSpentPreviousWeek(startOfPreviousWeek: Long, endOfPreviousWeek: Long): Float
}