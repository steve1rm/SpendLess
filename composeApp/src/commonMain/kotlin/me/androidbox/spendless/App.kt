package me.androidbox.spendless

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
fun App() {

    MaterialTheme {
        val navController = rememberNavController()
        val mainViewModel = koinViewModel<MainViewModel>()

  /*      println("USERNAME: ${mainState.hasUsername}")
        println("HAS ACTIVE SESSION: ${mainState.isSessionActive}")*/

        // No login credentials => show login screen
        // login credentials and expired => pin prompt screen
        // login credentials and not expired => dashboard screen
        // navigate from widget => dashboard screen open transactions

        val mainState by mainViewModel.mainState.collectAsStateWithLifecycle(initialValue = MainState.Loading)

        when(val status = mainState) {
           MainState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MainState.Success -> {
                NavHost(
                    navController = navController,
                    startDestination = if(status.isSessionActive) Route.DashboardGraph else Route.AuthenticationGraph
                ) {

                    this.authentication(navController)

                    this.dashboardGraph(navController)

                    this.settingsGraph(navController)
                }
            }
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