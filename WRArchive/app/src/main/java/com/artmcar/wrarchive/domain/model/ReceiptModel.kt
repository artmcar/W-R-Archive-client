package com.artmcar.wrarchive.domain.model

import com.artmcar.wrarchive.data.local.room.SyncStatus

data class ReceiptModel(
    val localId: Int,
    val remoteId: Int?,
    val title: String,
    val description: String,
    val purchaseDate: Long,
    val imagePath: String?,
    val createdAt: Long,
    val syncStatus: SyncStatus
)