package com.artmcar.wrarchive.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponseDto(
    val imageUrl: String
)