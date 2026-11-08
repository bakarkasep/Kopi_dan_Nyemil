package com.kopinyemil.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kopinyemil.app.components.AppBottomNavigation
import com.kopinyemil.app.screens.ConfirmationScreen
import com.kopinyemil.app.screens.HomeScreen
import com.kopinyemil.app.screens.LoginScreen
import com.kopinyemil.app.screens.OrderScreen
import com.kopinyemil.app.screens.ProfileScreen
import com.kopinyemil.app.screens.RegisterScreen
import com.kopinyemil.app.screens.SignUpScreen
import com.kopinyemil.app.screens.WelcomeScreen
import com.kopinyemil.app.viewmodel.AppViewModel

private val tabRoutes = setOf(Routes.HOME, Routes.ORDER, Routes.PROFILE)

/** Pindah ke tujuan dan hapus seluruh back stack sebelumnya. */
private fun NavHostController.navigateClearingStack(route: String) {
    navigate(route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
fun AppNavigation(vm: AppViewModel) {
    val navController = rememberNavController()
    val startDestination = remember { if (vm.isLoggedIn) Routes.HOME else Routes.WELCOME }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in tabRoutes) {
                AppBottomNavigation(currentRoute, vm.cartCount) { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(inner)
        ) {
            composable(Routes.WELCOME) {
                WelcomeScreen(onStart = { navController.navigate(Routes.SIGNUP) })
            }
            composable(Routes.SIGNUP) {
                SignUpScreen(
                    onRegister = { navController.navigate(Routes.REGISTER) },
                    onLogin = { navController.navigate(Routes.LOGIN) }
                )
            }
            composable(Routes.LOGIN) {
                LoginScreen(vm, onSuccess = { navController.navigateClearingStack(Routes.HOME) })
            }
            composable(Routes.REGISTER) {
                RegisterScreen(vm, onSuccess = { navController.navigateClearingStack(Routes.HOME) })
            }
            composable(Routes.HOME) {
                HomeScreen(vm, onCheckout = {
                    navController.navigate(Routes.ORDER) {
                        popUpTo(Routes.HOME) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
            composable(Routes.ORDER) {
                OrderScreen(vm, onOrderPlaced = { navController.navigate(Routes.CONFIRMATION) {
                    popUpTo(Routes.HOME)
                    launchSingleTop = true
                } })
            }
            composable(Routes.PROFILE) {
                ProfileScreen(vm, onLogout = { navController.navigateClearingStack(Routes.WELCOME) })
            }
            composable(Routes.CONFIRMATION) {
                ConfirmationScreen(vm, onDone = { navController.navigateClearingStack(Routes.HOME) })
            }
        }
    }
}
