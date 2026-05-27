package com.artmcar.wrarchive.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        private val DARK_THEME = booleanPreferencesKey("dark_theme")
        private val CLOUD_SYNC = booleanPreferencesKey("cloud_sync")
        private val REMEMBER_LOGIN = booleanPreferencesKey("remember_login")
    }
    val darkThemeFlow: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_THEME] ?: false }
    val cloudSyncFlow: Flow<Boolean> =
        context.dataStore.data.map { it[CLOUD_SYNC] ?: false }
    val rememberLoginFlow: Flow<Boolean> =
        context.dataStore.data.map { it[REMEMBER_LOGIN] ?: false }
    suspend fun setDarkTheme(enabled: Boolean)
    {
        context.dataStore.edit { it[DARK_THEME] = enabled }
    }
    suspend fun setCloudSync(enabled: Boolean)
    {
        context.dataStore.edit { it[CLOUD_SYNC] = enabled }
    }
    suspend fun setRememberLogin(enabled: Boolean)
    {
        context.dataStore.edit { it[REMEMBER_LOGIN] = enabled }
    }
}