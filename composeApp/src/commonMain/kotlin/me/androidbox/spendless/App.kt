package me.androidbox.spendless

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import me.androidbox.spendless.authentication.presentation.CreatePinEvents
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.screens.CreatePinScreen
import me.androidbox.spendless.core.presentation.ObserveAsEvents
import me.androidbox.spendless.navigation.Route
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
                startDestination = Route.PinCreateScreen
            ) {
                composable<Route.PinCreateScreen> {
                    val pinViewModel = koinViewModel<PinViewModel>()
                    val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()

                    ObserveAsEvents(pinViewModel.pinChannel) { createPinEvents ->
                        when(createPinEvents) {
                            is CreatePinEvents.HasInvalidPin -> {
                                println("ObserveEvent ${createPinEvents.isValid}")

                                if(createPinEvents.isValid) {
                                    /** Navigate to the onboarding screen valid pin */
                                    navController.navigate(route = Route.OnBoardingScreen)
                                }
                                else {
                                    /** Show red banner invalid repeated pin */
                                }
                            }
                        }
                    }

                    CreatePinScreen(
                        createPinState = pinState,
                        onAction = pinViewModel::onAction)
                }
            }
        }
    }
}