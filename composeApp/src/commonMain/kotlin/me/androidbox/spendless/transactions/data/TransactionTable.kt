package me.androidbox.spendless.transactions.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactionTable")
data class TransactionTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: Int,
    val counterParty: String,
    val category: Int,
    val note: String,
    val createAt: Long,
    val amount: Long)

