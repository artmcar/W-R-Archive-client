package com.artmcar.wrarchive.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptResponseDto(
    val id: Int,
    val title: String,
    val description: String,
    val purchaseDate: Long,
    val imagePath: String?,
    val createdAt: Long
)