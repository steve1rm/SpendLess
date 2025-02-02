package me.androidbox.spendless.di

import me.androidbox.spendless.authentication.presentation.PinViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendLessModule = module {
    viewModelOf(::PinViewModel)
}
