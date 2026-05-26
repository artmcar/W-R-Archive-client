package com.artmcar.wrarchive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.auth.LoginScreen
import com.artmcar.wrarchive.presentation.auth.RegisterScreen
import com.artmcar.wrarchive.presentation.main.MainScreen
import com.artmcar.wrarchive.presentation.profile.ProfileScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthGraph
    ) {
        navigation<AuthGraph>(
            startDestination = LoginRoute
        ) {
            composable<LoginRoute> {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate(
                            MainRoute
                        ) {
                            popUpTo(AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(
                            RegisterRoute
                        )
                    }
                )
            }
            composable<RegisterRoute> {
                RegisterScreen(
                    onLoginClick = {
                        navController.popBackStack()
                    },
                    onRegisterClick = {
                        navController.navigate(
                            MainRoute
                        ) {
                            popUpTo(AuthGraph) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable<MainRoute> {
            MainScreen(
                onProfileClick = {
                    navController.navigate(
                        ProfileRoute
                    )
                }
            )
        }
        composable<ProfileRoute> {
            ProfileScreen(
                onLogout = {
                    navController.navigate(
                        AuthGraph
                    ) {
                        popUpTo(0)
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}