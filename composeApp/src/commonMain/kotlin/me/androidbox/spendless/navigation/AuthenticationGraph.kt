package me.androidbox.spendless.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.compose.runtime.getValue
import me.androidbox.spendless.authentication.presentation.AuthenticationAction
import me.androidbox.spendless.authentication.presentation.AuthenticationSharedViewModel
import me.androidbox.spendless.authentication.presentation.CreatePinActions
import me.androidbox.spendless.authentication.presentation.CreatePinEvent
import me.androidbox.spendless.authentication.presentation.LoginAction
import me.androidbox.spendless.authentication.presentation.LoginViewModel
import me.androidbox.spendless.authentication.presentation.PinViewModel
import me.androidbox.spendless.authentication.presentation.RegisterAction.OnLoginClicked
import me.androidbox.spendless.authentication.presentation.RegisterAction.OnRegisterClicked
import me.androidbox.spendless.authentication.presentation.RegisterEvent
import me.androidbox.spendless.authentication.presentation.RegisterViewModel
import me.androidbox.spendless.authentication.presentation.screens.CreatePinScreen
import me.androidbox.spendless.authentication.presentation.screens.LoginScreen
import me.androidbox.spendless.authentication.presentation.screens.PinPromptScreen
import me.androidbox.spendless.authentication.presentation.screens.RegisterScreen
import me.androidbox.spendless.core.presentation.ObserveAsEvents
import me.androidbox.spendless.onboarding.screens.PreferenceOnboardingScreen
import me.androidbox.spendless.onboarding.screens.PreferenceViewModel
import me.androidbox.spendless.onboarding.screens.components.PreferenceContent
import me.androidbox.spendless.sharedViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import me.androidbox.spendless.onboarding.screens.PreferenceAction

fun NavGraphBuilder.authentication(navController: NavController) {
    navigation<Route.AuthenticationGraph>(
        startDestination = Route.LoginScreen
    ) {

        this.composable<Route.LoginScreen> {
            val loginViewModel = koinViewModel<LoginViewModel>()
            val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

            LoginScreen(
                loginState = loginState,
                action = { loginAction ->
                    when (loginAction) {
                        LoginAction.OnRegisterClicked -> {
                            navController.navigate(Route.RegisterScreen) {
                                popUpTo(Route.LoginScreen) {
                                    this.inclusive = true
                                    this.saveState = true
                                }
                                restoreState = true
                            }
                        }

                        else -> {
                            loginViewModel.action(loginAction)
                        }
                    }
                }
            )
        }

        composable<Route.RegisterScreen> {
            val registerViewModel = koinViewModel<RegisterViewModel>()
            val registerState by registerViewModel.registerState.collectAsStateWithLifecycle()
            val authenticationSharedViewModel = it.sharedViewModel<AuthenticationSharedViewModel>(navController)

            ObserveAsEvents(registerViewModel.registerChannel) { registerEvent ->
                when (registerEvent) {
                    RegisterEvent.OnRegisterFailure -> {
                        /**
                        registerViewModel.onAction(RegisterAction.ShouldShowRedBanner(show = true))
                         **/
                    }

                    is RegisterEvent.OnRegisterSuccess -> {
                        authenticationSharedViewModel.onAction(AuthenticationAction.OnUsernameEntered(registerEvent.username))
                        navController.navigate(Route.PinCreateScreen)
                    }
                }
            }

            RegisterScreen(
                registerState = registerState,
                action = { registerAction ->
                    when (registerAction) {
                        OnLoginClicked -> {
                            navController.navigate(Route.LoginScreen) {
                                this.popUpTo(Route.RegisterScreen) {
                                    this.inclusive = true
                                    this.saveState = true
                                }
                                this.restoreState = true
                            }
                        }

                        OnRegisterClicked -> {
                            authenticationSharedViewModel.onAction(AuthenticationAction.OnUsernameEntered(registerState.username))
                            navController.navigate(Route.PinCreateScreen)
                        }

                        else -> {
                            registerViewModel.action(registerAction)
                        }
                    }
                }
            )
        }

        composable<Route.PinCreateScreen> {
            val pinViewModel = koinViewModel<PinViewModel>()
            val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()
            val authenticationSharedViewModel = it.sharedViewModel<AuthenticationSharedViewModel>(navController)

            ObserveAsEvents(pinViewModel.pinChannel) { createPinEvent ->
                when (createPinEvent) {
                    is CreatePinEvent.PinEntryEvent -> {
                        println("ObserveEvent ${createPinEvent.isValid}")

                        if (createPinEvent.isValid) {
                            /** Navigate to the onboarding screen valid pin */
                            println("Navigate to onboarding")
                            authenticationSharedViewModel.onAction(AuthenticationAction.OnPinEntered(createPinEvent.pin))
                            navController.navigate(Route.PreferenceOnBoardingScreen)
                        } else {
                            /** Show red banner if user has entered an incorrect pin */
                            println("Show Red banner")
                            pinViewModel.onAction(
                                CreatePinActions.ShouldShowRedBanner(
                                    showBanner = true
                                )
                            )
                        }
                    }
                }
            }

            CreatePinScreen(
                createPinState = pinState,
                onAction = { createPinActions ->
                    when (createPinActions) {
                        CreatePinActions.OnBackPressed -> {
                            navController.navigate(Route.RegisterScreen)
                        }

                        else -> {
                            pinViewModel.onAction(createPinActions)
                        }
                    }
                })
        }

        composable<Route.PinPromptScreen> {
            val pinViewModel = koinViewModel<PinViewModel>()
            val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()

            PinPromptScreen(
                createPinState = pinState,
                onAction = pinViewModel::onAction
            )
        }

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
            val preferenceViewModel = koinViewModel<PreferenceViewModel>()
            val onboardingPreferenceState by preferenceViewModel.preferenceState.collectAsStateWithLifecycle()
            val authenticationSharedViewModel = it.sharedViewModel<AuthenticationSharedViewModel>(navController = navController)

            PreferenceOnboardingScreen(
                preferenceContent = {
                    PreferenceContent(
                        preferenceState = onboardingPreferenceState,
                        action = preferenceViewModel::onAction
                    )
                },
                onBackClicked = {
                    navController.navigate(Route.PinCreateScreen) {
                        this.popUpTo(Route.PreferenceOnBoardingScreen)
                    }
                },
                isEnabled = onboardingPreferenceState.isEnabled,
                onStartTrackingClicked = {
                    authenticationSharedViewModel.saveCredentials()
                    preferenceViewModel.savePreferences()
                    navController.navigate(Route.DashboardGraph) {
                        this.popUpTo(Route.AuthenticationGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
