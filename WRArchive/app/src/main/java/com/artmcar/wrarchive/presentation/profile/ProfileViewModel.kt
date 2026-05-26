package com.artmcar.wrarchive.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.data.preferences.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    init {
        observeSettings()
    }
    private fun observeSettings() {
        viewModelScope.launch {
            combine(
                settingsManager.darkThemeFlow,
                settingsManager.cloudSyncFlow,
                settingsManager.rememberLoginFlow
            ) { dark, cloud, remember ->
                ProfileUiState(
                    darkTheme = dark,
                    cloudSync = cloud,
                    rememberLogin = remember
                )
            }.collect { state ->
                _uiState.update {
                    state
                }
            }
        }
    }
    fun updateDarkTheme(value: Boolean) {
        viewModelScope.launch {
            settingsManager.setDarkTheme(value)
        }
    }
    fun updateCloudSync(value: Boolean) {
        viewModelScope.launch {
            settingsManager.setCloudSync(value)
        }
    }
    fun updateRememberLogin(value: Boolean) {
        viewModelScope.launch {
            settingsManager.setRememberLogin(value)
        }
    }
}