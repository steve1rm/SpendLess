package me.androidbox.spendless.settings.domain.imp

import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase

class InsertPreferenceUseCaseImp(private val spendLessDataSource: SpendLessDataSource) : InsertPreferenceUseCase {
    override suspend fun execute(preferenceTable: PreferenceTable) {
        spendLessDataSource.insertPreference(preferenceTable)
    }
}