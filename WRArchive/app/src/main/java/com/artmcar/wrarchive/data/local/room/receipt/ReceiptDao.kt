package com.artmcar.wrarchive.data.local.room.receipt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}