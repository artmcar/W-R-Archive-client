package com.artmcar.wrarchive.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WarrantyRequestDto(
    val title: String,
    val description: String,
    val expirationDate: Long,
    val imagePath: String?
)