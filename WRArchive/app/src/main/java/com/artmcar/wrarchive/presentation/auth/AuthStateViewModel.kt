package com.artmcar.wrarchive.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.repository.AuthRepository
import com.artmcar.wrarchive.domain.usecase.auth.ValidateSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthStateViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateSessionUseCase: ValidateSessionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState(isLoading = true))
    val state = _state.asStateFlow()
    init {
        validateSession()
        observeAuth()
    }
    private fun validateSession() {
        viewModelScope.launch {
            validateSessionUseCase()
        }
    }
    private fun observeAuth() {
        viewModelScope
            .launch{
                authRepository.isLoggedInFlow.collect{
                    loggedIn -> _state.value = AuthState(isAuthorized = loggedIn, isLoading = false)
                }
            }
    }
}