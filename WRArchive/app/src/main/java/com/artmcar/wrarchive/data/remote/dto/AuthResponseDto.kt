package com.artmcar.wrarchive.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponseDto(
    val token: String,
    val email: String
)