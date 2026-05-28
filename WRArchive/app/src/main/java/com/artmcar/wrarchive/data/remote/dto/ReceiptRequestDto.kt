package com.artmcar.wrarchive.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptRequestDto(
    val title: String,
    val description: String,
    val purchaseDate: Long,
    val imagePath: String?
)