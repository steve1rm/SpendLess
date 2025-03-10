package me.androidbox.spendless

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import me.androidbox.spendless.navigation.Route
import me.androidbox.spendless.navigation.authentication
import me.androidbox.spendless.navigation.dashboardGraph
import me.androidbox.spendless.navigation.settingsGraph
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(shouldNavigateOnWidget: Boolean = false) {
    MaterialTheme {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.DashboardGraph // if(shouldNavigateOnWidget) Route.DashboardGraph else Route.AuthenticationGraph
        ) {

            this.authentication(navController)

            this.dashboardGraph(navController, shouldNavigateOnWidget)

            this.settingsGraph(navController)
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = this.destination.parent?.route ?: return koinViewModel<T>()

    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(viewModelStoreOwner = parentEntry)
}