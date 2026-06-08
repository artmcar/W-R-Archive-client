package com.artmcar.wrarchive

import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import org.junit.Assert.assertEquals
import org.junit.Test

class WarrantyModelTest {
    @Test
    fun warranty_with_null_remote_id_can_be_created() {
        val warranty = WarrantyModel(
            localId = 1,
            remoteId = null,
            title = "Laptop",
            description = "",
            expirationDate = 100,
            imagePath = null,
            createdAt = 0,
            syncStatus = SyncStatus.CREATED
        )
        assertEquals(
            SyncStatus.CREATED,
            warranty.syncStatus
        )
    }
}