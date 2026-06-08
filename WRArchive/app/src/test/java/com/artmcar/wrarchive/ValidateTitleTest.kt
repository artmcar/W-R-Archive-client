package com.artmcar.wrarchive

import com.artmcar.wrarchive.domain.validation.ValidateTitle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateTitleTest {
    private val validator = ValidateTitle()

    @Test
    fun empty_title_returns_error() {
        val result = validator("")
        assertFalse(result.isSuccessful)
        assertEquals(
            R.string.title_empty,
            result.errorMessageRes
        )
    }

    @Test
    fun valid_title_returns_success() {
        val result = validator("Laptop")
        assertTrue(result.isSuccessful)
        assertNull(result.errorMessageRes)
    }
}