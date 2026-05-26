package com.artmcar.wrarchive.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warranties")
data class WarrantyFields(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val remoteId: Int? = null,
    val title: String,
    val description: String,
    val expirationDate: Long,
    val imagePath: String?,
    val createdAt: Long,
    val syncStatus: SyncStatus
)