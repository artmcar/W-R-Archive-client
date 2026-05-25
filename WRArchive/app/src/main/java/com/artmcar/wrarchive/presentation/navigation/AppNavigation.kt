package com.artmcar.wrarchive.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.auth.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.Main.route)
                },
                onRegisterClick = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }
//        composable(Routes.Register.route) {
//            RegisterScreen(
//                onRegisterSuccess = {
//                    navController.popBackStack()
//                },
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }
//        composable(Routes.Main.route) {
//            MainScreen(navController)
//        }
//        composable(Routes.Profile.route) {
//            ProfileScreen(
//                onLogout = {
//                    navController.navigate(Routes.Login.route){
//                        popUpTo(0)
//                    }
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
//        }
    }
}