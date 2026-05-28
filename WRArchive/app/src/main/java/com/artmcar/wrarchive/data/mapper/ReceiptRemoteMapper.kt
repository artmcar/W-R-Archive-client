package com.artmcar.wrarchive.data.mapper

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptFields
import com.artmcar.wrarchive.data.remote.dto.ReceiptResponseDto

fun ReceiptResponseDto.toEntity(): ReceiptFields {
    return ReceiptFields(
        localId = 0,
        remoteId = id,
        title = title,
        description = description,
        purchaseDate = purchaseDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = SyncStatus.SYNCED
    )
}