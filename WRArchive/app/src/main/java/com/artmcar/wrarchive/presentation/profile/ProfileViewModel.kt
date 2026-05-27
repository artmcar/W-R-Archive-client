package com.artmcar.wrarchive.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.usecase.settings_uc.GetCloudSyncUseCase
import com.artmcar.wrarchive.domain.usecase.settings_uc.GetDarkThemeUseCase
import com.artmcar.wrarchive.domain.usecase.settings_uc.GetRememberLoginUseCase
import com.artmcar.wrarchive.domain.usecase.settings_uc.SetCloudSyncUseCase
import com.artmcar.wrarchive.domain.usecase.settings_uc.SetDarkThemeUseCase
import com.artmcar.wrarchive.domain.usecase.settings_uc.SetRememberLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
    private val getCloudSyncUseCase: GetCloudSyncUseCase,
    private val getRememberLoginUseCase: GetRememberLoginUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val setCloudSyncUseCase: SetCloudSyncUseCase,
    private val setRememberLoginUseCase: SetRememberLoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    init {
        observeSettings()
    }
    private fun observeSettings() {
        viewModelScope.launch {
            combine(
                getDarkThemeUseCase(),
                getCloudSyncUseCase(),
                getRememberLoginUseCase()
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
    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            setDarkThemeUseCase(enabled)
        }
    }
    fun setCloudSync(enabled: Boolean) {
        viewModelScope.launch {
            setCloudSyncUseCase(enabled)
        }
    }
    fun setRememberLogin(enabled: Boolean) {
        viewModelScope.launch {
            setRememberLoginUseCase(enabled)
        }
    }
}