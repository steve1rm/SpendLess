package me.androidbox.spendless.di

import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.SpendLessDataSourceImpl
import me.androidbox.spendless.data.SpendLessDatabase
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendLessModule = module {
    factory<SpendLessDataSource> {
        SpendLessDataSourceImpl(
            get<_root_ide_package_.me.androidbox.spendless.data.SpendLessDatabase>()
        )
    }

    viewModelOf(::PinViewModel)
}
