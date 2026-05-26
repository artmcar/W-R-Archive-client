package com.artmcar.wrarchive.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WarrantyDao {

    //Todo Реализовать запросы

    @Insert
    suspend fun insert(item: WarrantyFields)

    @Update
    suspend fun update(item: WarrantyFields)

    @Query("SELECT * FROM warranties ORDER BY expirationDate ASC")
    fun getAll(): Flow<List<WarrantyFields>>

    @Query("SELECT * FROM warranties WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncItems(): List<WarrantyFields>
}