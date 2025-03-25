package me.androidbox.spendless

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import me.androidbox.spendless.core.data.SpendLessDatabase
import org.koin.dsl.module

val androidSpecificModule = module {
    single<SpendLessDatabase> {
        Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SpendLessDatabase::class.java
        ).allowMainThreadQueries().build()
    }
}
