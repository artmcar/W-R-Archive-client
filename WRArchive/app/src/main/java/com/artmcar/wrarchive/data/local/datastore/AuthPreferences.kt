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
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
    }
    val isLoggedInFlow: Flow<Boolean> = context.authDataStore.data.map { it[IS_LOGGED_IN] ?: false }
    val userEmailFlow: Flow<String?> = context.authDataStore.data.map { it[USER_EMAIL] }
    val tokenFlow: Flow<String?> = context.authDataStore.data.map { it[JWT_TOKEN] }
    suspend fun login(email: String, token: String)
    {
        context.authDataStore.edit {
            it[IS_LOGGED_IN] = true
            it[USER_EMAIL] = email
            it[JWT_TOKEN] = token
        }
    }
    suspend fun logout() {
        context.authDataStore.edit {
            it[IS_LOGGED_IN] = false
            it.remove(USER_EMAIL)
            it.remove(JWT_TOKEN)
        }
    }
}