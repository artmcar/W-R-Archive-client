package com.artmcar.wrarchive.presentation.main

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artmcar.wrarchive.presentation.navigation.AddEditReceiptRoute
import com.artmcar.wrarchive.presentation.navigation.AddEditWarrantyRoute
import com.artmcar.wrarchive.presentation.navigation.ProfileRoute
import com.artmcar.wrarchive.presentation.navigation.ReceiptRoute
import com.artmcar.wrarchive.presentation.navigation.WarrantyRoute
import com.artmcar.wrarchive.presentation.receipt.ReceiptScreen
import com.artmcar.wrarchive.presentation.receipt.add_edit_screen.AddEditReceiptScreen
import com.artmcar.wrarchive.presentation.warranty.WarrantyScreen
import com.artmcar.wrarchive.presentation.warranty.add_edit_screen.AddEditWarrantyScreen

@Composable
fun MainScreen (
    onProfileClick: () -> Unit
){
    val navController = rememberNavController()
    val items = listOf(BottomNavItem.Warranty, BottomNavItem.Receipt)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar =
        currentDestination?.route == WarrantyRoute::class.qualifiedName ||
                currentDestination?.route == ReceiptRoute::class.qualifiedName
    Scaffold(
        bottomBar = {
            if(showBottomBar){
                NavigationBar{
                    items.forEach{item ->
                        NavigationBarItem(
                            selected = currentDestination
                                ?.hierarchy
                                ?.any {
                                    it.route == item.route::class.qualifiedName
                                } == true,
                            onClick = {
                                navController.navigate(item.route){
                                    popUpTo(navController
                                        .graph
                                        .findStartDestination()
                                        .id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = stringResource(item.labelRes)
                                )
                            },
                            label = {Text(stringResource(item.labelRes))}
                        )
                    }
                }
            }
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
                    onProfileClick = onProfileClick,
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
                    onProfileClick = onProfileClick,
                    onAddClick = {
                        navController.navigate(AddEditReceiptRoute())
                    },
                    onEditClick = { id ->
                        navController.navigate(AddEditReceiptRoute(id))
                    }
                )
            }
            composable<AddEditWarrantyRoute> {
                AddEditWarrantyScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable<AddEditReceiptRoute> {
                AddEditReceiptScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}