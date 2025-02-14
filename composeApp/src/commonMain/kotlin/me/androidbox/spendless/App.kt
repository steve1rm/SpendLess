package me.androidbox.spendless

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import me.androidbox.spendless.authentication.presentation.CreatePinActions
import me.androidbox.spendless.authentication.presentation.CreatePinEvents
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.screens.CreatePinScreen
import me.androidbox.spendless.authentication.presentation.screens.PinPromptScreen
import me.androidbox.spendless.core.presentation.ObserveAsEvents
import me.androidbox.spendless.dashboard.DashboardScreen
import me.androidbox.spendless.navigation.Route
import me.androidbox.spendless.onboarding.screens.PreferenceScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.AuthenticationGraph
        ) {
            navigation<Route.AuthenticationGraph>(
                startDestination = Route.DashboardScreen
            ) {
                composable<Route.PinCreateScreen> {
                    val pinViewModel = koinViewModel<PinViewModel>()
                    val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()

                    ObserveAsEvents(pinViewModel.pinChannel) { createPinEvents ->
                        when (createPinEvents) {
                            is CreatePinEvents.PinEntryEvent -> {
                                println("ObserveEvent ${createPinEvents.isValid}")

                                if (createPinEvents.isValid) {
                                    /** Navigate to the onboarding screen valid pin */
                                    println("Navigate to onboarding")
                                } else {
                                    /** Show red banner if user has entered an incorrect pin */
                                    println("Show Red banner")
                                    pinViewModel.onAction(CreatePinActions.ShouldShowRedBanner(showBanner = true))
                                }
                            }
                        }
                    }

                    CreatePinScreen(
                        createPinState = pinState,
                        onAction = pinViewModel::onAction)
                }

                composable<Route.PinPromptScreen> {
                    val pinViewModel = koinViewModel<PinViewModel>()
                    val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()

                    PinPromptScreen(
                        createPinState = pinState,
                        onAction = pinViewModel::onAction
                    )
                }

                composable<Route.PreferenceScreen> {
                    PreferenceScreen()
                }

                composable<Route.DashboardScreen> {
                    DashboardScreen(
                        onSettingsClicked = {

                        },
                        onAddTransaction = {}
                    )
                }
            }
        }
    }
}