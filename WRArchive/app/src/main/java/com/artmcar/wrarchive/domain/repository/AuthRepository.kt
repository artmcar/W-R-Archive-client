package com.artmcar.wrarchive.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedInFlow: Flow<Boolean>
    val userEmailFlow: Flow<String?>
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String): Boolean
    suspend fun logout()
}