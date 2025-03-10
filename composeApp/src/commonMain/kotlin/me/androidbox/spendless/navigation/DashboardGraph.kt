package me.androidbox.spendless.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.presentation.screens.AllTransactionScreen
import me.androidbox.spendless.dashboard.presentation.screens.DashboardScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.dashboardGraph(navController: NavController, shouldNavigateOnWidget: Boolean = false) {
    this.navigation<Route.DashboardGraph>(
        startDestination = Route.DashboardScreen
    ) {

        composable<Route.DashboardScreen> {
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

          /*  if(shouldNavigateOnWidget) {
                dashBoardViewModel.onAction(DashboardAction.OpenNewTransaction(shouldOpen = true))
            }*/

            DashboardScreen(
                shouldNavigateOnWidget = shouldNavigateOnWidget,
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

        composable<Route.AllTransactionsScreen>(
            deepLinks = listOf(navDeepLink {
                uriPattern = "spendLess://dashboard"
            })
        ) {
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            AllTransactionScreen(
                transactions = dashboardState.listOfTransactions,
                onNavigationClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}