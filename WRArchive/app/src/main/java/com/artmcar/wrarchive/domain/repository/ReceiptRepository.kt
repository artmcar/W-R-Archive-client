package com.artmcar.wrarchive.domain.repository

import com.artmcar.wrarchive.domain.model.ReceiptModel
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    fun getAllReceipts(): Flow<List<ReceiptModel>>
    suspend fun getReceiptById(id: Int): ReceiptModel?
    suspend fun insertReceipt(item: ReceiptModel)
    suspend fun updateReceipt(item: ReceiptModel)
    suspend fun deleteReceipt(item: ReceiptModel)
}