package com.artmcar.wrarchive.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Verified
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
        icon = Icons.Default.Verified,
        route = WarrantyRoute
    )
    data object Receipt : BottomNavItem(
        labelRes = R.string.receipt_label,
        icon = Icons.Default.ReceiptLong,
        route = ReceiptRoute
    )
}