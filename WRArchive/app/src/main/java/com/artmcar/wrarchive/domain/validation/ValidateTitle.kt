package com.artmcar.wrarchive.domain.validation

import com.artmcar.wrarchive.R
import javax.inject.Inject

class ValidateTitle @Inject constructor() {
    operator fun invoke(
        title: String
    ): ValidationResult {
        if(title.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessageRes = R.string.title_empty
            )
        }
        if(title.length < 3) {
            return ValidationResult(
                isSuccessful = false,
                errorMessageRes = R.string.title_short
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}