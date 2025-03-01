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
<<<<<<< HEAD
import org.koin.core.module.dsl.factoryOf
=======
>>>>>>> 1f1e1d14dbd8567ee7b1093a285e279d5c2cb016
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
<<<<<<< HEAD
=======
    }

    factory<SpendLessDataSource> {
        SpendLessDataSourceImpl(
            get<SpendLessDatabase>()
        )
    }

    factory<Repository> {
        RepositoryImp()
>>>>>>> 1f1e1d14dbd8567ee7b1093a285e279d5c2cb016
    }

    factory<FetchAllTransactionsUseCase> {
        FetchAllTransactionsUseCaseImp(get<Repository>())
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
