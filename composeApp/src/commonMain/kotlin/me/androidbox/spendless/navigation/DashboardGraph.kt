package me.androidbox.spendless.navigation

import androidx.compose.foundation.lazy.layout.getDefaultLazyLayoutKey
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.spendless.dashboard.presentation.screens.AllTransactionListScreen
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.presentation.screens.AllTransactionScreen
import me.androidbox.spendless.dashboard.presentation.screens.DashboardScreen
import me.androidbox.spendless.transactions.TransactionViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.dashboardGraph(navController: NavController) {
    this.navigation<Route.DashboardGraph>(
        startDestination = Route.DashboardScreen
    ) {

        composable<Route.DashboardScreen> {
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()
            val transactionViewModel = koinViewModel<TransactionViewModel>()

            dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            DashboardScreen(
                dashboardState = dashboardState,
                dashboardAction = { dashboardAction ->
                    when(dashboardAction) {
                        DashboardAction.OnShowAllClicked -> {
                            navController.navigate(route = Route.AllTransactionsScreen)
                        }
                        else -> {
                            dashBoardViewModel.onAction(dashboardAction)
                        }
                    }
                },
            )
        }

        composable<Route.AllTransactionsScreen> {
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            AllTransactionScreen(
                transactions = dashboardState.listOfTransactions,
                onNavigationClicked = {
                    navController.popBackStack()
                }
            )
/*
            val transactionViewModel = koinViewModel<TransactionViewModel>()
            val transactionState by transactionViewModel.transactionState.collectAsStateWithLifecycle()
*/


/*
            AllTransactionListScreen(
                transactionState = transactionState
            )
*/
        }
    }
}