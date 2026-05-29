package com.artmcar.wrarchive.data.repository

import android.util.Log
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptDao
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyDao
import com.artmcar.wrarchive.data.mapper.toEntity
import com.artmcar.wrarchive.data.remote.api.ReceiptApi
import com.artmcar.wrarchive.data.remote.api.UploadApi
import com.artmcar.wrarchive.data.remote.api.WarrantyApi
import com.artmcar.wrarchive.data.remote.dto.ReceiptRequestDto
import com.artmcar.wrarchive.data.remote.dto.WarrantyRequestDto
import com.artmcar.wrarchive.domain.repository.AuthRepository
import com.artmcar.wrarchive.domain.repository.SyncRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    private val warrantyDao: WarrantyDao,
    private val receiptDao: ReceiptDao,
    private val warrantyApi: WarrantyApi,
    private val receiptApi: ReceiptApi,
    private val authRepository: AuthRepository,
    private val uploadApi: UploadApi
) : SyncRepository {
    override suspend fun syncPendingData() {
        withContext(Dispatchers.IO) {
            val token = authRepository.tokenFlow.firstOrNull() ?: return@withContext
            syncWarranties(token)
            syncReceipts(token)
        }
    }
    private suspend fun syncWarranties(token: String)
    {
        val pending = warrantyDao.getPendingSyncItems()
        pending.forEach { item ->
            try {
                when(item.syncStatus) {
                    SyncStatus.CREATED -> {
                        val uploadedImage =
                            item.imagePath?.let {path ->
                                if( path.startsWith("/uploads"))
                                {
                                    path
                                }
                                else {
                                    uploadApi.uploadImage(token, path)
                                }
                            }
                        val response =
                            warrantyApi.createWarranty(
                                token = token,
                                dto = WarrantyRequestDto(
                                    title = item.title,
                                    description = item.description,
                                    expirationDate = item.expirationDate,
                                    imagePath = uploadedImage
                                )
                            )
                        warrantyDao.update(
                            item.copy(
                                remoteId = response.id,
                                imagePath = uploadedImage,
                                syncStatus = SyncStatus.SYNCED
                            )
                        )
                    }
                    SyncStatus.UPDATED -> {
                        val uploadedImage =
                            item.imagePath?.let { path ->
                                if( path.startsWith("/uploads"))
                                {
                                    path
                                }
                                else {
                                    uploadApi.uploadImage(token, path)
                                }
                            }
                        val remoteId = item.remoteId ?: return@forEach
                        warrantyApi.updateWarranty(
                            token = token,
                            id = remoteId,
                            dto = WarrantyRequestDto(
                                title = item.title,
                                description = item.description,
                                expirationDate = item.expirationDate,
                                imagePath = uploadedImage
                            )
                        )
                        warrantyDao.update(
                            item.copy(
                                imagePath = uploadedImage,
                                syncStatus = SyncStatus.SYNCED
                            )
                        )
                    }
                    SyncStatus.DELETED -> {
                        val remoteId = item.remoteId ?: return@forEach
                        warrantyApi.deleteWarranty(
                            token = token,
                            id = remoteId
                        )
                        warrantyDao.delete(item)
                    }
                    SyncStatus.SYNCED -> Unit
                }
            } catch (e: Exception) {
                Log.e(
                    "SYNC_ERROR",
                    e.stackTraceToString()
                )
            }
        }
    }
    private suspend fun syncReceipts(
        token: String
    ) {
        val pending =
            receiptDao.getPendingSyncItems()
        pending.forEach { item ->
            try {
                when(item.syncStatus) {
                    SyncStatus.CREATED -> {
                        val uploadedImage =
                            item.imagePath?.let {
                                if( it.startsWith("/uploads"))
                                {
                                    it
                                }
                                else {
                                    uploadApi.uploadImage(token, it)
                                }
                            }
                        val response =
                            receiptApi.createReceipt(
                                token = token,
                                dto = ReceiptRequestDto(
                                    title = item.title,
                                    description = item.description,
                                    purchaseDate = item.purchaseDate,
                                    imagePath = uploadedImage
                                )
                            )
                        receiptDao.update(
                            item.copy(
                                remoteId = response.id,
                                imagePath = uploadedImage,
                                syncStatus = SyncStatus.SYNCED
                            )
                        )
                    }
                    SyncStatus.UPDATED -> {
                        val uploadedImage =
                            item.imagePath?.let {
                                if( it.startsWith("/uploads"))
                                {
                                    it
                                }
                                else {
                                    uploadApi.uploadImage(token, it)
                                }
                            }
                        val remoteId = item.remoteId ?: return@forEach
                        receiptApi.updateReceipt(
                            token = token,
                            id = remoteId,
                            dto = ReceiptRequestDto(
                                title = item.title,
                                description = item.description,
                                purchaseDate = item.purchaseDate,
                                imagePath = uploadedImage
                            )
                        )
                        receiptDao.update(
                            item.copy(
                                imagePath = uploadedImage,
                                syncStatus = SyncStatus.SYNCED
                            )
                        )
                    }
                    SyncStatus.DELETED -> {
                        val remoteId = item.remoteId ?: return@forEach
                        receiptApi.deleteReceipt(
                            token = token,
                            id = remoteId
                        )
                        receiptDao.delete(item)
                    }
                    SyncStatus.SYNCED -> Unit
                }
            } catch (e: Exception) {
                Log.e(
                    "SYNC_ERROR",
                    e.stackTraceToString()
                )
            }
        }
    }
    override suspend fun downloadRemoteData() {
        withContext(Dispatchers.IO) {
            val token =
                authRepository
                    .tokenFlow
                    .firstOrNull()
                    ?: return@withContext
            downloadWarranties(token)
            downloadReceipts(token)
        }
    }
    private suspend fun downloadWarranties(
        token: String
    ) {
        try {
            val remoteItems = warrantyApi.getAllWarranties(token)
            warrantyDao.upsertAll(remoteItems.map { it.toEntity() })
        } catch (e: Exception) {
            Log.e(
                "SYNC_ERROR",
                e.stackTraceToString()
            )
        }
    }
    private suspend fun downloadReceipts(
        token: String
    ) {
        try {
            val remoteItems = receiptApi.getAllReceipts(token)
            receiptDao.upsertAll(remoteItems.map { it.toEntity() })
        } catch (e: Exception) {
            Log.e(
                "SYNC_ERROR",
                e.stackTraceToString()
            )
        }
    }
}