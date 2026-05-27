package com.artmcar.wrarchive.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val darkThemeFlow: Flow<Boolean>
    val cloudSyncFlow: Flow<Boolean>
    val rememberLoginFlow: Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
    suspend fun setCloudSync(enabled: Boolean)
    suspend fun setRememberLogin(enabled: Boolean)
    fun getRememberLogin(): Flow<Boolean>
}