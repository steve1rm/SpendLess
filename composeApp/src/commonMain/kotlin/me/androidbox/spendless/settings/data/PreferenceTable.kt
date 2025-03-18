package me.androidbox.spendless.settings.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PreferenceTable(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val expensesFormat: Int,
    val currency: Int,
    val decimalSeparator: Int,
    val thousandsSeparator: Int
)
