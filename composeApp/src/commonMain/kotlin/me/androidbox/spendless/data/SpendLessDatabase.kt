package me.androidbox.spendless.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Transaction::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class SpendLessDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun userDao(): UserDao
}