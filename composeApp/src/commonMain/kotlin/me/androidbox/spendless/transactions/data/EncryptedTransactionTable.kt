package me.androidbox.spendless.transactions.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "encryptedTransactionTable")
data class EncryptedTransactionTable(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val encryptedString: String)

