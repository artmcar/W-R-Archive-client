package com.artmcar.wrarchive.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.domain.usecase.auth.RegisterUseCase
import com.artmcar.wrarchive.presentation.auth.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(email = value)
        }
    }
    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }
    fun register(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val success = registerUseCase(_uiState.value.email, _uiState.value.password)
            _uiState.update { it.copy(isLoading = false) }
            if(success) { onSuccess() }
            else {
                _uiState.update { it.copy(errorRes = R.string.register_failed) }
            }
        }
    }
}