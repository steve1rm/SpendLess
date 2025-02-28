package me.androidbox.spendless.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.compose.runtime.getValue
import me.androidbox.spendless.dashboard.AllTransactionListScreen
import me.androidbox.spendless.dashboard.DashBoardViewModel
import me.androidbox.spendless.dashboard.DashboardScreen
import me.androidbox.spendless.onboarding.screens.PreferenceOnboardingScreen
import me.androidbox.spendless.onboarding.screens.components.PreferenceContent
import me.androidbox.spendless.transactions.TransactionViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.dashboardGraph(navController: NavController) {
    this.navigation<Route.DashboardGraph>(
        startDestination = Route.PreferenceOnBoardingScreen
    ) {
        composable<Route.PreferenceOnBoardingScreen>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            PreferenceOnboardingScreen(
                preferenceContent = {
                    PreferenceContent(
                        onExpenseFormatClicked = {
                            println(it.type)
                        },
                        onCurrencyClicked = {
                            println(it.symbol)
                        },
                        onDecimalSeparatorClicked = {
                            println(it.type)
                        },
                        onThousandsSeparator = {
                            println(it.type)
                        },
                        money = 6347238245
                    )
                },
                onBackClicked = {
                    navController.navigate(Route.PinCreateScreen) {
                        this.popUpTo(Route.PreferenceOnBoardingScreen)
                    }
                }
            )
        }

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