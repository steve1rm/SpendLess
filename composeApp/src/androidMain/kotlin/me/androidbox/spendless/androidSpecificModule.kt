package me.androidbox.spendless

import androidx.room.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import me.androidbox.spendless.core.data.SpendLessDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidSpecificModule = module {

    single<SpendLessDatabase> {
        val dbFile = androidContext().getDatabasePath("spendLessJournal.db")

        Room.databaseBuilder<SpendLessDatabase>(
            context = androidContext(),
            name = dbFile.absolutePath)
            .setDriver(AndroidSQLiteDriver())
            .build()
    }

    single<SpendLessPreference> {
        SpendLessPreferenceImp(androidContext())
    }
}
