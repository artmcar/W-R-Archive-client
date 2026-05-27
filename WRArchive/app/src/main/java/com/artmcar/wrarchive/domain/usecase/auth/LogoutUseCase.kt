package com.artmcar.wrarchive.domain.usecase.auth

import com.artmcar.wrarchive.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}