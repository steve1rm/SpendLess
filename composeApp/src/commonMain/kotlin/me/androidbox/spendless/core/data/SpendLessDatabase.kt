package me.androidbox.spendless.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.spendless.data.Transaction
import me.androidbox.spendless.data.TransactionDao
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.data.UserDao

@Database(entities = [Transaction::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class SpendLessDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
}