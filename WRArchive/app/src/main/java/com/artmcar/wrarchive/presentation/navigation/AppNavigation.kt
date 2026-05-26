package com.artmcar.wrarchive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.auth.LoginScreen
import com.artmcar.wrarchive.presentation.auth.RegisterScreen
import com.artmcar.wrarchive.presentation.main.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthGraph) {
        navigation<AuthGraph>(
            startDestination = LoginRoute
        ){
            composable<LoginRoute> {
                LoginScreen(
                    onLoginClick = {
                        navController.navigate(MainGraph){
                            popUpTo(AuthGraph){
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(RegisterRoute)
                    }
                )
            }
            composable<RegisterRoute> {
                RegisterScreen(
                    onLoginClick = {
                        navController.popBackStack()
                    },
                    onRegisterClick = {
                        navController.navigate(MainGraph){
                            popUpTo(AuthGraph){
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        navigation<MainGraph>(
            startDestination = WarrantyRoute
        ){
            composable<WarrantyRoute> {
                MainScreen(
                    rootNavController = navController
                )
            }
            composable<ProfileRoute> {
                ProfileScreen(
                    onLogout = {
                        navController.navigate(AuthGraph){
                            popUpTo(MainGraph){
                                inclusive = true
                            }
                        }
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}