package me.androidbox.spendless.di

import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.transactions.TransactionViewModel
import me.androidbox.spendless.transactions.data.RepositoryImp
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCaseImp
import me.androidbox.spendless.transactions.domain.Repository
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.data.SpendLessDataSourceImpl
import me.androidbox.spendless.data.SpendLessDatabase
import org.koin.core.module.dsl.factoryOf
import me.androidbox.spendless.domain.CreateTransactionUseCase
import me.androidbox.spendless.domain.CreateUserUseCase
import me.androidbox.spendless.domain.imp.CreateTransactionUseCaseImp
import me.androidbox.spendless.domain.imp.CreateUserUseCaseImp
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val spendLessModule = module {

    factory<CreateUserUseCase> {
        CreateUserUseCaseImp(
            get<SpendLessDataSource>()
        )
    }

    factory<CreateTransactionUseCase> {
        CreateTransactionUseCaseImp(
            get<SpendLessDataSource>()
        )
    }

    factory<Repository> {
        RepositoryImp()
    }

    factory<FetchAllTransactionsUseCase> {
        FetchAllTransactionsUseCaseImp(get<Repository>())
    }

    factory<CreateUserUseCase> {
        CreateUserUseCaseImp(
            get<SpendLessDataSource>()
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

    viewModelOf(::PinViewModel)
    viewModelOf(::TransactionViewModel)
    viewModelOf(::DashBoardViewModel)
}
