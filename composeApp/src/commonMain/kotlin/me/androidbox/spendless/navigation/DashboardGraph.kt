package me.androidbox.spendless.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.presentation.screens.AllTransactionScreen
import me.androidbox.spendless.dashboard.presentation.screens.DashboardScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.dashboardGraph(navController: NavController) {
    this.navigation<Route.DashboardGraph>(
        startDestination = Route.DashboardScreen(openTransaction = 0)
    ) {

        composable<Route.DashboardScreen>(
           deepLinks = listOf(navDeepLink {
                uriPattern = "spendLess://dashboard/{openTransaction}"
            })) {

            val shouldOpenTransaction = it.toRoute<Route.DashboardScreen>().openTransaction
            val dashBoardViewModel = koinViewModel<DashBoardViewModel>()
            val dashboardState by dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            dashBoardViewModel.dashboardState.collectAsStateWithLifecycle()

            DashboardScreen(
                shouldNavigateOnWidget = shouldOpenTransaction == 1,
                dashboardState = dashboardState,
                dashboardAction = { dashboardAction ->
                    when(dashboardAction) {
                        DashboardAction.OnShowAllClicked -> {
                            navController.navigate(route = Route.AllTransactionsScreen)
                        }
                        DashboardAction.OpenSettings -> {
                            navController.navigate(route = Route.SettingsGraph)
                        }
                        else -> {
                            dashBoardViewModel.onAction(dashboardAction)
                        }
                    }
                },
            )
        }

        composable<Route.AllTransactionsScreen>() {
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