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

    @Query("SELECT * FROM transactionTable WHERE type = 0 ORDER BY amount ASC LIMIT 1")
    fun getLargestTransaction(): Flow<TransactionTable>

    @Query("""
        SELECT SUM(amount)
        FROM transactionTable
        WHERE createAt BETWEEN :startOfPreviousWeek AND :endOfPreviousWeek
    """)
    suspend fun getTotalSpentPreviousWeek(startOfPreviousWeek: Long, endOfPreviousWeek: Long): Double

    @Query("SELECT SUM(amount) FROM transactionTable")
    fun getTotalTransactionAmount(): Flow<Double>

    @Query("""
        SELECT *
        FROM transactionTable
        GROUP BY category
        ORDER BY COUNT(category) DESC
        LIMIT 1
    """)
    fun getMostPopularCategory(): Flow<TransactionTable>
}