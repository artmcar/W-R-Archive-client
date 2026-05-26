package com.artmcar.wrarchive.data.mapper

import com.artmcar.wrarchive.data.local.room.receipt.ReceiptFields
import com.artmcar.wrarchive.domain.model.ReceiptModel

fun ReceiptFields.toDomain(): ReceiptModel {
    return ReceiptModel(
        localId = localId,
        remoteId = remoteId,
        title = title,
        description = description,
        purchaseDate = purchaseDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = syncStatus
    )
}

fun ReceiptModel.toEntity(): ReceiptFields {
    return ReceiptFields(
        localId = localId,
        remoteId = remoteId,
        title = title,
        description = description,
        purchaseDate = purchaseDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = syncStatus
    )
}