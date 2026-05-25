package com.artmcar.wrarchive.presentation.navigation

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Warranty : Routes("warranty")
    object Receipt : Routes("receipt")
    object Profile : Routes("profile")
}
