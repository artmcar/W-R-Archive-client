package com.artmcar.wrarchive.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.usecase.settings_uc.GetDarkThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getDarkThemeUseCase: GetDarkThemeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState = _uiState.asStateFlow()
    init {
        observeTheme()
    }
    private fun observeTheme() {
        viewModelScope.launch {
            getDarkThemeUseCase().collectLatest { isDark ->
                _uiState.update {
                    it.copy(isDarkTheme = isDark)
                }
            }
        }
    }
}