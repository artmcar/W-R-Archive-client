package com.artmcar.wrarchive.presentation.auth

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorRes: Int? = null
)