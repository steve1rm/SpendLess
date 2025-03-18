package me.androidbox.spendless.di

import me.androidbox.spendless.MainViewModel
import me.androidbox.spendless.SpendLessPreference
import me.androidbox.spendless.authentication.domain.GetUserUseCase
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
import me.androidbox.spendless.authentication.domain.ValidateUserUseCase
import me.androidbox.spendless.authentication.domain.imp.GetUserUseCaseImp
import me.androidbox.spendless.transactions.domain.imp.InsertTransactionUseCaseImp
import me.androidbox.spendless.authentication.domain.imp.InsertUserUseCaseImp
import me.androidbox.spendless.authentication.domain.imp.ValidateUserUseCaseImp
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.settings.domain.FetchPreferenceUseCase
import me.androidbox.spendless.settings.domain.InsertPreferenceUseCase
import me.androidbox.spendless.settings.domain.imp.FetchPreferenceUseCaseImp
import me.androidbox.spendless.settings.domain.imp.InsertPreferenceUseCaseImp
import me.androidbox.spendless.settings.presentation.SettingsViewModel
import me.androidbox.spendless.transactions.TransactionViewModel
import me.androidbox.spendless.transactions.data.RepositoryImp
import me.androidbox.spendless.transactions.domain.FetchAllTransactionsUseCase
import me.androidbox.spendless.transactions.domain.FetchLargestTransactionUseCase
import me.androidbox.spendless.transactions.domain.FetchMostPopularCategoryUseCase
import me.androidbox.spendless.transactions.domain.FetchTotalSpentPreviousWeekUseCase
import me.androidbox.spendless.transactions.domain.imp.FetchAllTransactionsUseCaseImp
import me.androidbox.spendless.transactions.domain.Repository
import me.androidbox.spendless.transactions.domain.imp.FetchLargestTransactionUseCaseImp
import me.androidbox.spendless.transactions.domain.imp.FetchMostPopularCategoryUseCaseImp
import me.androidbox.spendless.transactions.domain.imp.FetchTotalSpentPreviousWeekUseCaseImp
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
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)

    viewModel {
        AuthenticationSharedViewModel(
            get<InsertUserUseCase>(),
            get<SpendLessPreference>()
        )
    }

    factory<InsertUserUseCase> {
        InsertUserUseCaseImp(get<SpendLessDataSource>())
    }

    factory<GetUserUseCase> {
        GetUserUseCaseImp(get<SpendLessDataSource>())
    }

    factory<ValidateUserUseCase> {
        ValidateUserUseCaseImp(get<SpendLessDataSource>())
    }

    factory<InsertPreferenceUseCase> {
        InsertPreferenceUseCaseImp(get<SpendLessDataSource>())
    }

    factory<FetchPreferenceUseCase> {
        FetchPreferenceUseCaseImp(get<SpendLessDataSource>())
    }

    factory<InsertTransactionUseCase> {
        InsertTransactionUseCaseImp(
            get<SpendLessDataSource>()
        )
    }

    factory<FetchAllTransactionsUseCase> {
        FetchAllTransactionsUseCaseImp(
            get<SpendLessDataSource>()
        )
    }

    factory<FetchTotalSpentPreviousWeekUseCase> {
        FetchTotalSpentPreviousWeekUseCaseImp(get<SpendLessDataSource>())
    }

    factory<FetchLargestTransactionUseCase> {
        FetchLargestTransactionUseCaseImp(get<SpendLessDataSource>())
    }

    factory<FetchMostPopularCategoryUseCase> {
        FetchMostPopularCategoryUseCaseImp(get<SpendLessDataSource>())
    }

    factory<SpendLessDataSource> {
        SpendLessDataSourceImpl(
            get<SpendLessDatabase>()
        )
    }

    factory<Repository> {
        RepositoryImp()
    }
}
