package me.androidbox.spendless.settings.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferenceDao {

    @Upsert
    suspend fun insertPreference(preferenceTable: PreferenceTable)

    @Query("SELECT * FROM PreferenceTable")
    fun getPreference(): Flow<PreferenceTable>
}