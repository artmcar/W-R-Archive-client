package com.artmcar.wrarchive.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.domain.repository.SyncRepository
import com.artmcar.wrarchive.domain.usecase.auth.LoginUseCase
import com.artmcar.wrarchive.presentation.auth.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val syncRepository: SyncRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()
    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(email = value)
        }
    }
    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val success = loginUseCase(_uiState.value.email, _uiState.value.password)
            _uiState.update {
                it.copy(isLoading = false)
            }
            if(success) {
                syncRepository.downloadRemoteData()
                onSuccess()
            }
            else { _uiState.update { it.copy(errorRes = R.string.login_failed) }
            }
        }
    }
}