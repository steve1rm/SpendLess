package me.androidbox.spendless

import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import me.androidbox.spendless.data.SpendLessDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidSpecificModule = module {
<<<<<<< HEAD
    single<SpendLessDatabase> {
        val dbFile = androidContext().getDatabasePath("spendLess.db")
=======

    single<SpendLessDatabase> {
        val dbFile = androidContext().getDatabasePath("spendLessJournal.db")
>>>>>>> 1f1e1d14dbd8567ee7b1093a285e279d5c2cb016

        Room.databaseBuilder<SpendLessDatabase>(
            context = androidContext(),
            name = dbFile.absolutePath)
            .setDriver(AndroidSQLiteDriver())
            .build()
    }
}
