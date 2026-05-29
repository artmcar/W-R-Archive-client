package com.artmcar.wrarchive.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.auth.AuthStateViewModel
import com.artmcar.wrarchive.presentation.auth.LoginScreen
import com.artmcar.wrarchive.presentation.auth.RegisterScreen
import com.artmcar.wrarchive.presentation.main.MainScreen
import com.artmcar.wrarchive.presentation.profile.ProfileScreen

@Composable
fun AppNavigation(authViewModel: AuthStateViewModel = hiltViewModel())
{
    val authState by authViewModel.state.collectAsState()
    if(authState.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
        return
    }
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if(authState.isAuthorized){ MainRoute } else {AuthGraph}
    ) {
        navigation<AuthGraph>(
            startDestination = LoginRoute
        ) {
            composable<LoginRoute> {
                LoginScreen(
                    onLoginSuccess = {
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
                    onRegisterSuccess = {
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