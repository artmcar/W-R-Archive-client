package com.artmcar.wrarchive.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.navigation.AddEditWarrantyRoute
import com.artmcar.wrarchive.presentation.navigation.ProfileRoute
import com.artmcar.wrarchive.presentation.navigation.ReceiptRoute
import com.artmcar.wrarchive.presentation.navigation.WarrantyRoute
import com.artmcar.wrarchive.presentation.warranty.WarrantyScreen

@Composable
fun MainScreen (
    rootNavController: NavHostController
){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            MainBottomBar(navController)
        }
    ){
        padding ->
        NavHost(
            navController = navController,
            startDestination = WarrantyRoute,
            modifier = Modifier.padding(padding)
        ){
            composable<WarrantyRoute>{
                WarrantyScreen(
                    onProfileClick = {
                        rootNavController.navigate(ProfileRoute)
                    },
                    onAddClick = {
                        navController.navigate(AddEditWarrantyRoute())
                    },
                    onEditClick = { id ->
                        navController.navigate(AddEditWarrantyRoute(id))
                    }
                )
            }
            composable<ReceiptRoute> {
                ReceiptScreen(
                    onProfileClick = {
                        rootNavController.navigate(ProfileRoute)
                    }
                )
            }
            composable<AddEditWarrantyRoute> {
                AddEditWarrantyRoute(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}