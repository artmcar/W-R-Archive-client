package com.artmcar.wrarchive.data.local.room.receipt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artmcar.wrarchive.data.local.room.SyncStatus


@Entity(tableName = "receipts")
data class ReceiptFields(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,
    val remoteId: Int?,
    val title: String,
    val description: String,
    val purchaseDate: Long,
    val imagePath: String?,
    val createdAt: Long,
    val syncStatus: SyncStatus
)