package me.androidbox.spendless.di

import androidx.lifecycle.viewmodel.compose.viewModel
import me.androidbox.spendless.authentication.presentation.AuthenticationSharedViewModel
import me.androidbox.spendless.authentication.presentation.LoginViewModel
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.RegisterViewModel
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.SpendLessDataSourceImpl
import me.androidbox.spendless.data.SpendLessDatabase
import me.androidbox.spendless.domain.CreateTransactionUseCase
import me.androidbox.spendless.domain.InsertUserUseCase
import me.androidbox.spendless.domain.imp.CreateTransactionUseCaseImp
import me.androidbox.spendless.domain.imp.InsertUserUseCaseImp
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.transactions.TransactionViewModel
import me.androidbox.spendless.transactions.data.RepositoryImp
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCaseImp
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

    factory<InsertUserUseCase> {
        InsertUserUseCaseImp(get<SpendLessDataSource>())
    }

    viewModel {
        AuthenticationSharedViewModel(
            get<InsertUserUseCase>()
        )
    }

    factory<CreateTransactionUseCase> {
        CreateTransactionUseCaseImp(
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
