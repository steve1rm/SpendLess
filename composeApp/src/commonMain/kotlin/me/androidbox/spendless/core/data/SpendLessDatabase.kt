package me.androidbox.spendless.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.spendless.transactions.data.TransactionTable
import me.androidbox.spendless.transactions.data.TransactionDao
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.data.UserDao
import me.androidbox.spendless.settings.data.PreferenceDao
import me.androidbox.spendless.settings.data.PreferenceTable

@Database(entities = [
    TransactionTable::class,
    User::class,
    PreferenceTable::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class SpendLessDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
    abstract fun preferenceDao(): PreferenceDao
}