package com.artmcar.wrarchive.data.mapper

import com.artmcar.wrarchive.data.local.room.WarrantyFields
import com.artmcar.wrarchive.domain.model.WarrantyModel

fun WarrantyFields.toDomain(): WarrantyModel {
    return WarrantyModel(
        localId = localId,
        remoteId = remoteId,
        title = title,
        description = description,
        expirationDate = expirationDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = syncStatus
    )
}

fun WarrantyModel.toEntity(): WarrantyFields {
    return WarrantyFields(
        localId = localId,
        remoteId = remoteId,
        title = title,
        description = description,
        expirationDate = expirationDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = syncStatus
    )
}