package com.artmcar.wrarchive.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.presentation.navigation.ReceiptRoute
import com.artmcar.wrarchive.presentation.navigation.WarrantyRoute

sealed class BottomNavItem(
    val labelRes: Int,
    val icon: ImageVector,
    val route: Any
) {
    data object Warranty : BottomNavItem(
        labelRes = R.string.warranty_label,
        //Todo Подобрать иконку
        icon = Icons.Default.Home,
        route = WarrantyRoute
    )
    data object Receipt : BottomNavItem(
        labelRes = R.string.receipt_label,
        //Todo Подобрать иконку
        icon = Icons.Default.Menu,
        route = ReceiptRoute
    )
}