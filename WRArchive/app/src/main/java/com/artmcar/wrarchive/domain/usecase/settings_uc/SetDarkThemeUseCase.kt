package com.artmcar.wrarchive.domain.usecase.settings_uc

import com.artmcar.wrarchive.domain.repository.SettingsRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(
        enabled: Boolean
    ) {
        repository.setDarkTheme(enabled)
    }
}