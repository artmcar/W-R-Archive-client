package com.artmcar.wrarchive.domain.repository

import com.artmcar.wrarchive.domain.model.WarrantyModel
import kotlinx.coroutines.flow.Flow

interface WarrantyRepository {
    fun getAllWarranties(): Flow<List<WarrantyModel>>
    suspend fun insertWarranty(item: WarrantyModel)
    suspend fun updateWarranty(item: WarrantyModel)
    suspend fun deleteWarranty(item: WarrantyModel)
}