package com.artmcar.wrarchive.domain.validation

import com.artmcar.wrarchive.R
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ValidateDate @Inject constructor() {
    operator fun invoke(
        date: String
    ): ValidationResult {
        if(date.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessageRes = R.string.date_empty
            )
        }
        return try {
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            formatter.isLenient = false
            formatter.parse(date)
            ValidationResult(isSuccessful = true
            )
        } catch (e: Exception) {
            ValidationResult(
                isSuccessful = false,
                errorMessageRes = R.string.invalid_date_format
            )
        }
    }
}