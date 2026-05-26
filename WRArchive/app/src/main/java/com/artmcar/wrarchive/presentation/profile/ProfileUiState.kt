package com.artmcar.wrarchive.presentation.profile

data class ProfileUiState(
    val darkTheme: Boolean = false,
    val cloudSync: Boolean = false,
    val rememberLogin: Boolean = false
)