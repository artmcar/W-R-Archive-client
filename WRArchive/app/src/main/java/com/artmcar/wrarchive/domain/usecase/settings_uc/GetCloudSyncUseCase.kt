package com.artmcar.wrarchive.domain.usecase.settings_uc

import com.artmcar.wrarchive.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCloudSyncUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.cloudSyncFlow
    }
}