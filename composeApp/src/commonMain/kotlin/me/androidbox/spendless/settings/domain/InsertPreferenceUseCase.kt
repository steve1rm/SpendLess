package me.androidbox.spendless.settings.domain

import me.androidbox.spendless.settings.data.PreferenceTable

fun interface InsertPreferenceUseCase {
    suspend fun execute(preferenceTable: PreferenceTable)
}