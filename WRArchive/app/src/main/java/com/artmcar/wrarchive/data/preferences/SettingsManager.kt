package com.artmcar.wrarchive.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    companion object {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val CLOUD_SYNC = booleanPreferencesKey("cloud_sync")
        val REMEMBER_LOGIN = booleanPreferencesKey("remember_login")
    }
    val darkThemeFlow = context.dataStore.data.map {
        it[DARK_THEME] ?: false
    }
    val cloudSyncFlow = context.dataStore.data.map {
        it[CLOUD_SYNC] ?: false
    }
    val rememberLoginFlow = context.dataStore.data.map {
        it[REMEMBER_LOGIN] ?: false
    }
    suspend fun setDarkTheme(value: Boolean) { context.dataStore.edit {
            it[DARK_THEME] = value
        }
    }
    suspend fun setCloudSync(value: Boolean) {
        context.dataStore.edit {
            it[CLOUD_SYNC] = value
        }
    }
    suspend fun setRememberLogin(value: Boolean) {
        context.dataStore.edit {
            it[REMEMBER_LOGIN] = value
        }
    }
}