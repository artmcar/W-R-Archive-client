package com.artmcar.wrarchive.data.local.room.receipt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyFields
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {
    @Insert
    suspend fun insert(item: ReceiptFields)

    @Update
    suspend fun update(item: ReceiptFields)

    @Query("SELECT * FROM receipts")
    fun getAll(): Flow<List<ReceiptFields>>

    @Query("SELECT * FROM receipts WHERE localId = :id")
    suspend fun getById(id: Int): ReceiptFields?

    @Query("SELECT * FROM receipts WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncItems(): List<ReceiptFields>

    @Delete
    suspend fun delete(item: ReceiptFields)

    @Upsert
    suspend fun upsertAll(items: List<ReceiptFields>)
}