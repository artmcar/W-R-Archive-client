package com.artmcar.wrarchive.domain.usecase.auth

import com.artmcar.wrarchive.domain.repository.SettingsRepository
import javax.inject.Inject

class SetRememberLoginUseCase @Inject constructor(
    private val repository: SettingsRepository)
{
    suspend operator fun invoke(enabled: Boolean) {
        repository.setRememberLogin(enabled)
    }
}