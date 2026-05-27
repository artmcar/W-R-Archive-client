package com.artmcar.wrarchive.domain.usecase.auth

import com.artmcar.wrarchive.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.register(email, password)
    }
}