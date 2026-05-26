package com.artmcar.wrarchive.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artmcar.wrarchive.data.local.converter.SyncStatusConverter

@Database(
    entities = [
        WarrantyFields::class
    ],
    version = 1
)
@TypeConverters(SyncStatusConverter::class)
abstract class WarrantyDatabase: RoomDatabase(){
    abstract fun warrantyDao(): WarrantyDao
}