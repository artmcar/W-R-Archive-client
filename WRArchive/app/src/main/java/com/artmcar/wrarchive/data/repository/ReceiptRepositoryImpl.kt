package com.artmcar.wrarchive.data.repository

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptDao
import com.artmcar.wrarchive.data.mapper.toDomain
import com.artmcar.wrarchive.data.mapper.toEntity
import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReceiptRepositoryImpl @Inject constructor(
    private val dao: ReceiptDao
) : ReceiptRepository {
    override fun getAllReceipts(): Flow<List<ReceiptModel>> {
        return dao.getAll().map { list ->
            list.filter { it.syncStatus != SyncStatus.DELETED }
                .map {
                    it.toDomain()
                }
        }
    }
    override suspend fun getReceiptById(id: Int): ReceiptModel? {
        return dao.getById(id)?.toDomain()
    }
    override suspend fun insertReceipt(
        item: ReceiptModel
    ) { dao.insert(item.toEntity())
    }
    override suspend fun updateReceipt(
        item: ReceiptModel
    ) {
        dao.update(
            item.toEntity()
        )
    }
    override suspend fun deleteReceipt(
        item: ReceiptModel
    ) {
        if(item.remoteId == null){
            dao.delete(item.toEntity())
        }
        else{
            dao.update(item.copy(syncStatus = SyncStatus.DELETED).toEntity())
        }
    }
}