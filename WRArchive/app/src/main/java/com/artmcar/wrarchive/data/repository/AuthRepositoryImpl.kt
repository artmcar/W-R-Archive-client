package com.artmcar.wrarchive.data.repository

import com.artmcar.wrarchive.data.local.datastore.AuthPreferences
import com.artmcar.wrarchive.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences) : AuthRepository
{
    override val isLoggedInFlow: Flow<Boolean> = authPreferences.isLoggedInFlow
    override val userEmailFlow: Flow<String?> = authPreferences.userEmailFlow
    override suspend fun login(email: String, password: String): Boolean {
        if(
            email.isBlank() ||
            password.isBlank()
        ) {
            return false
        }
        authPreferences.login(email)
        return true
    }
    override suspend fun register(email: String, password: String): Boolean {
        if(
            email.isBlank() ||
            password.isBlank()
        ) {
            return false
        }
        authPreferences.login(email)
        return true
    }
    override suspend fun logout() {
        authPreferences.logout()
    }
}