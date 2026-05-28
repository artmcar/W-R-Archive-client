package com.artmcar.wrarchive.data.local.room.warranty

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
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

    @Query("SELECT * FROM warranties WHERE localId = :id")
    suspend fun getById(id: Int):WarrantyFields?

    @Query("SELECT * FROM warranties WHERE syncStatus != 'SYNCED'")
    suspend fun getPendingSyncItems(): List<WarrantyFields>

    @Delete
    suspend fun delete(item: WarrantyFields)

    @Upsert
    suspend fun upsertAll(items: List<WarrantyFields>)

}