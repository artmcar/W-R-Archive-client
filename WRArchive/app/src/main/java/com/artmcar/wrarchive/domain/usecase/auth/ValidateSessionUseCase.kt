package com.artmcar.wrarchive.domain.usecase.auth

import com.artmcar.wrarchive.domain.repository.AuthRepository
import com.artmcar.wrarchive.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ValidateSessionUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        val rememberLogin = settingsRepository.getRememberLogin().first()
        if(!rememberLogin) {
            authRepository.logout()
        }
    }
}