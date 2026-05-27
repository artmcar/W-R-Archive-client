package com.artmcar.wrarchive.domain.validation

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessageRes: Int? = null
)