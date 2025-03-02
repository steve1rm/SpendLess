package me.androidbox.spendless.settings.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PreferenceDao {

    @Upsert
    suspend fun insertPreference(preferenceTable: PreferenceTable)

    @Query("SELECT * FROM PreferenceTable")
    suspend fun getPreference(): PreferenceTable
}