package com.artmcar.wrarchive.data.local.converter

import androidx.room.TypeConverter
import com.artmcar.wrarchive.data.local.room.SyncStatus

class SyncStatusConverter {
    @TypeConverter
    fun fromSyncStatus(status: SyncStatus):String{
        return status.name
    }
    @TypeConverter
    fun toSyncStatus(value: String):SyncStatus{
        return SyncStatus.valueOf(value)
    }
}