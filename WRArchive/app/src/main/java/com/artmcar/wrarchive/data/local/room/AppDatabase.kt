package com.artmcar.wrarchive.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.artmcar.wrarchive.data.local.converter.SyncStatusConverter
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptDao
import com.artmcar.wrarchive.data.local.room.receipt.ReceiptFields
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyDao
import com.artmcar.wrarchive.data.local.room.warranty.WarrantyFields

@Database(
    entities = [
        WarrantyFields::class,
        ReceiptFields::class
    ],
    version = 1
)
@TypeConverters(SyncStatusConverter::class)
abstract class AppDatabase: RoomDatabase(){
    abstract fun warrantyDao(): WarrantyDao
    abstract fun receiptDao(): ReceiptDao
}