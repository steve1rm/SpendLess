package me.androidbox.spendless

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.screens.CreatePinScreen
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

                    CreatePinScreen(
                        listOfPinNumbers = listOf(3, 5, 9, 4, 1)
                    ) { keyButton ->
                        println("Create Pin $keyButton")
                    }
                }
            }
        }
    }
}