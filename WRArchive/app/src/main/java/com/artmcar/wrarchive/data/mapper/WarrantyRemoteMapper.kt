package com.artmcar.wrarchive.data.mapper

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyFields
import com.artmcar.wrarchive.data.remote.dto.WarrantyResponseDto

fun WarrantyResponseDto.toEntity(): WarrantyFields {
    return WarrantyFields(
        localId = 0,
        remoteId = id,
        title = title,
        description = description,
        expirationDate = expirationDate,
        imagePath = imagePath,
        createdAt = createdAt,
        syncStatus = SyncStatus.SYNCED
    )
}