package com.artmcar.wrarchive.data.repository

import com.artmcar.wrarchive.data.local.datastore.AuthPreferences
import com.artmcar.wrarchive.data.remote.api.AuthApi
import com.artmcar.wrarchive.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val authApi: AuthApi
) : AuthRepository
{
    override val isLoggedInFlow: Flow<Boolean> = authPreferences.isLoggedInFlow
    override val userEmailFlow: Flow<String?> = authPreferences.userEmailFlow
    override val tokenFlow: Flow<String?> = authPreferences.tokenFlow

    override suspend fun login(email: String, password: String): Boolean
    {
        return try {
            val response = authApi.login(email, password)
            authPreferences.login(response.email, response.token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    override suspend fun register(email: String, password: String): Boolean
    {
        return try {
            val response = authApi.register(email, password)
            authPreferences.login(response.email, response.token)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    override suspend fun logout() {
        authPreferences.logout()
    }
}