package me.androidbox.spendless.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.compose.runtime.getValue
import me.androidbox.spendless.authentication.presentation.AuthenticationSharedViewModel
import me.androidbox.spendless.dashboard.AllTransactionListScreen
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.dashboard.screens.DashboardScreen
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.onboarding.screens.PreferenceOnboardingScreen
import me.androidbox.spendless.onboarding.screens.components.PreferenceContent
import me.androidbox.spendless.sharedViewModel
import me.androidbox.spendless.transactions.TransactionViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.dashboardGraph(navController: NavController) {
    this.navigation<Route.DashboardGraph>(
        startDestination = Route.DashboardScreen
    ) {

        composable<Route.DashboardScreen> {
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            DashboardScreen(
                dashboardState = dashboardState,
                dashboardAction = dashBoardViewModel::onAction
            )
        }

        composable<Route.CreateTransactionContent> {
            val transactionViewModel = koinViewModel<TransactionViewModel>()
            val transactionState by transactionViewModel.transactionState.collectAsStateWithLifecycle()

            /*  CreateTransactionContent(
                state = transactionState,
                action = transactionViewModel::onAction
            )*/
        }

        composable<Route.AllTransactionScreen> {
            val transactionViewModel = koinViewModel<TransactionViewModel>()
            val transactionState by transactionViewModel.transactionState.collectAsStateWithLifecycle()

            AllTransactionListScreen(
                transactionState = transactionState
            )
        }
    }
}