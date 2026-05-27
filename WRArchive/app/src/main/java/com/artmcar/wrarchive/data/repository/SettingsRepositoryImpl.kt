package com.artmcar.wrarchive.data.repository

import com.artmcar.wrarchive.data.local.datastore.SettingsDataStore
import com.artmcar.wrarchive.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: SettingsDataStore) : SettingsRepository {
    override val darkThemeFlow: Flow<Boolean> = dataStore.darkThemeFlow
    override val cloudSyncFlow: Flow<Boolean> = dataStore.cloudSyncFlow
    override val rememberLoginFlow: Flow<Boolean> = dataStore.rememberLoginFlow
    override suspend fun setDarkTheme(
        enabled: Boolean
    ) {
        dataStore.setDarkTheme(enabled)
    }
    override suspend fun setCloudSync(
        enabled: Boolean
    ) {
        dataStore.setCloudSync(enabled)
    }
    override suspend fun setRememberLogin(
        enabled: Boolean
    ) {
        dataStore.setRememberLogin(enabled)
    }
    override fun getRememberLogin(): Flow<Boolean> {
        return dataStore.rememberLoginFlow
    }
}