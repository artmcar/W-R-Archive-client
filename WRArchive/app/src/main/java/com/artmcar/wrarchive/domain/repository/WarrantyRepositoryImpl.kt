package com.artmcar.wrarchive.domain.repository

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyDao
import com.artmcar.wrarchive.data.mapper.toDomain
import com.artmcar.wrarchive.data.mapper.toEntity
import com.artmcar.wrarchive.domain.model.WarrantyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WarrantyRepositoryImpl @Inject constructor(
    private val dao: WarrantyDao
) : WarrantyRepository {

    override fun getAllWarranties(): Flow<List<WarrantyModel>> {
        return dao.getAll().map { list ->
            list.filter { it.syncStatus != SyncStatus.DELETED }
                .map {
                    it.toDomain()
                }
        }
    }
    override suspend fun getWarrantyById(id: Int): WarrantyModel? {
        return dao.getById(id)?.toDomain()
    }
    override suspend fun insertWarranty(item: WarrantyModel) {
        dao.insert(item.toEntity())
    }
    override suspend fun updateWarranty(item: WarrantyModel) {
        dao.update(
            item.copy(
                syncStatus = SyncStatus.UPDATED
            ).toEntity()
        )
    }
    override suspend fun deleteWarranty(item: WarrantyModel) {
        dao.update(
            item.copy(
                syncStatus = SyncStatus.DELETED
            ).toEntity()
        )
    }
}