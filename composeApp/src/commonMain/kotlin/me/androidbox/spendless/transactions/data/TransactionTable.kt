package me.androidbox.spendless.transactions.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionTable")
data class TransactionTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val counterparty: List<String>?,
    val amount: Float,
    val note: String?,
    val categoryId: Int,
    val createdAt: Long
)

