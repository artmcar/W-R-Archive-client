package com.artmcar.wrarchive.domain.usecase.auth

import com.artmcar.wrarchive.domain.repository.SettingsRepository
import javax.inject.Inject

class GetRememberLoginUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke() = repository.getRememberLogin()
}