package com.artmcar.wrarchive.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.presentation.navigation.ReceiptRoute
import com.artmcar.wrarchive.presentation.navigation.WarrantyRoute

@Composable
fun MainBottomBar(
    navController: NavController
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == WarrantyRoute::class.qualifiedName,
            onClick = {
                navController.navigate(WarrantyRoute){
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    //Todo Подобрать иконку
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.warranty_label))
            }
        )
        NavigationBarItem(
            selected = currentRoute == ReceiptRoute::class.qualifiedName,
            onClick = {
                navController.navigate(ReceiptRoute){
                    launchSingleTop = true
                }
            },
            icon = {
                Icon(
                    //Todo Подобрать иконку
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.receipt_label))
            }
        )
    }
}