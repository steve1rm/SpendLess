package me.androidbox.spendless.di

import me.androidbox.spendless.authentication.presentation.AuthenticationSharedViewModel
import me.androidbox.spendless.authentication.presentation.LoginViewModel
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.RegisterViewModel
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.core.data.SpendLessDataSource
import me.androidbox.spendless.core.data.imp.SpendLessDataSourceImpl
import me.androidbox.spendless.core.data.SpendLessDatabase
import me.androidbox.spendless.transactions.domain.InsertTransactionUseCase
import me.androidbox.spendless.authentication.domain.InsertUserUseCase
import me.androidbox.spendless.transactions.domain.imp.InsertTransactionUseCaseImp
import me.androidbox.spendless.authentication.domain.imp.InsertUserUseCaseImp
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase
import me.androidbox.spendless.settings.domain.imp.InsertPreferenceUseCaseImp
import me.androidbox.spendless.transactions.TransactionViewModel
import me.androidbox.spendless.transactions.data.RepositoryImp
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.imp.FetchAllTransactionsUseCaseImp
import me.androidbox.spendless.transactions.domain.Repository
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendLessModule = module {
    viewModelOf(::PinViewModel)
    viewModelOf(::TransactionViewModel)
    viewModelOf(::DashBoardViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::PreferenceViewModel)

    viewModel {
        AuthenticationSharedViewModel(
            get<InsertUserUseCase>()
        )
    }

    factory<InsertUserUseCase> {
        InsertUserUseCaseImp(get<SpendLessDataSource>())
    }

    factory<InsertPreferenceUseCase> {
        InsertPreferenceUseCaseImp(get<SpendLessDataSource>())
    }

    factory<InsertTransactionUseCase> {
        InsertTransactionUseCaseImp(
            get<SpendLessDataSource>()
        )
    }

    factory<SpendLessDataSource> {
        SpendLessDataSourceImpl(
            get<SpendLessDatabase>()
        )
    }

    factory<Repository> {
        RepositoryImp()
    }

    factory<FetchAllTransactionsUseCase> {
        FetchAllTransactionsUseCaseImp(get<Repository>())
    }
}
