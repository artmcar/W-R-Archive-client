package com.artmcar.wrarchive.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.authDataStore by preferencesDataStore(name = "auth_preferences")
class AuthPreferences @Inject constructor(

    @ApplicationContext
    private val context: Context
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val REMEMBER_LOGIN = booleanPreferencesKey("remember_login")
    }
    val isLoggedInFlow: Flow<Boolean> = context.authDataStore.data.map { it[IS_LOGGED_IN] ?: false }
    val userEmailFlow: Flow<String?> = context.authDataStore.data.map { it[USER_EMAIL] }
    val rememberLoginFlow: Flow<Boolean> = context.authDataStore.data.map { it[REMEMBER_LOGIN] ?: false }
    suspend fun login(email: String)
    {
        context.authDataStore.edit {
            it[IS_LOGGED_IN] = true
            it[USER_EMAIL] = email
        }
    }
    suspend fun logout() {
        context.authDataStore.edit {
            it[IS_LOGGED_IN] = false
            it[REMEMBER_LOGIN] = false
            it.remove(USER_EMAIL)
        }
    }
    suspend fun setRememberLogin(enabled: Boolean) {
        context.authDataStore.edit {
            it[REMEMBER_LOGIN] = enabled
        }
    }
}