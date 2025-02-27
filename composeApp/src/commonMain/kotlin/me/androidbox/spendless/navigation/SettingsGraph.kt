package me.androidbox.spendless.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.spendless.onboarding.screens.components.PreferenceContent
import me.androidbox.spendless.settings.presentation.PreferenceSettingsScreen
import me.androidbox.spendless.settings.presentation.SecurityScreen
import me.androidbox.spendless.settings.presentation.SettingsScreen

fun NavGraphBuilder.settingsGraph(navController: NavController) {

    this.navigation<Route.SettingsGraph>(
        startDestination = Route.SettingsScreen) {

        composable<Route.SettingsScreen> {
            SettingsScreen(
                onPreferenceClicked = {
                    navController.navigate(Route.PreferenceSettingsScreen)
                },
                onSecurityClicked = {
                    navController.navigate(Route.SecurityScreen)
                },
                onLogoutClicked = {

                },
                onBackClicked = {
                    navController.popBackStack()
                })
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
            PreferenceSettingsScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                preferenceContent = {
                    PreferenceContent()
                }
            )
        }
    }
}