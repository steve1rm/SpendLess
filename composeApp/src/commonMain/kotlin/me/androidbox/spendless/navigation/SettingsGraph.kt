package me.androidbox.spendless.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.spendless.core.presentation.ObserveAsEvents
import me.androidbox.spendless.dashboard.presentation.screens.DashboardScreen
import me.androidbox.spendless.onboarding.screens.PreferenceEvent
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.onboarding.screens.components.PreferenceContent
import me.androidbox.spendless.settings.presentation.PreferenceSettingsScreen
import me.androidbox.spendless.settings.presentation.SecurityScreen
import me.androidbox.spendless.settings.presentation.SettingsAction
import me.androidbox.spendless.settings.presentation.SettingsEvent
import me.androidbox.spendless.settings.presentation.SettingsScreen
import me.androidbox.spendless.settings.presentation.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.settingsGraph(navController: NavController) {

    this.navigation<Route.SettingsGraph>(
        startDestination = Route.SettingsScreen) {
        println("SETTINGS GRAPH")
        composable<Route.SettingsScreen> {
            val settingsViewModel = koinViewModel<SettingsViewModel>()

            ObserveAsEvents(
                flow = settingsViewModel.preferenceChannel,
                onEvent = { settingsEvent ->
                    when(settingsEvent) {
                        SettingsEvent.OnLogoutSuccess -> {
                            navController.navigate(route = Route.AuthenticationGraph) {
                                this.popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            )

            SettingsScreen(
                onPreferenceClicked = {
                    navController.navigate(Route.PreferenceSettingsScreen)
                },
                onSecurityClicked = {
                    navController.navigate(Route.SecurityScreen)
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                onAction = settingsViewModel::settingsAction)
        }

        composable<Route.SecurityScreen>(
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
            SecurityScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.PreferenceSettingsScreen>(
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
            val preferenceViewModel = koinViewModel<PreferenceViewModel>()
            val preferenceState by preferenceViewModel.preferenceState.collectAsStateWithLifecycle()

            ObserveAsEvents(
                flow = preferenceViewModel.preferenceChannel,
                onEvent = { preferenceEvent ->
                    when(preferenceEvent) {
                        PreferenceEvent.OnSavePreferences -> {
                            navController.popBackStack()
                        }
                    }
                }
            )

            PreferenceSettingsScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                preferenceContent = {
                    PreferenceContent(
                        preferenceState = preferenceState,
                        action = preferenceViewModel::onAction
                    )
                },
                canSavePreferences = preferenceState.isEnabled,
                action = preferenceViewModel::onAction
            )
        }
    }
}