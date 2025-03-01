package me.androidbox.spendless.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "counterparty") val counterparty: List<String>?,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "category") val categoryId: Int,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)

