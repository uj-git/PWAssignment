package com.test.pwassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.pwassignment.auth.AuthManager
import com.test.pwassignment.presentation.screens.home.ui.HomeScreen
import com.test.pwassignment.presentation.screens.login.ui.LoginScreen
import com.test.pwassignment.presentation.screens.notification_settings.NotificationAndSettings


@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    val startDestination =
        if (AuthManager.isLoggedIn()) Routes.Home.route
        else Routes.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(
                onNotificationClick = {
                    navController.navigate(Routes.Notification.route)
                }
            )
        }

        composable(Routes.Notification.route) {
            NotificationAndSettings(
                onNavBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    AuthManager.logout()
                    navController.navigate(Routes.Login.route)
                }
            )
        }
    }
}
