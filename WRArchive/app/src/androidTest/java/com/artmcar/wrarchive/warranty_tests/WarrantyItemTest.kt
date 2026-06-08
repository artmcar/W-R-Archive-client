package com.artmcar.wrarchive.warranty_tests

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.presentation.warranty.WarrantyItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WarrantyItemTest {
    @get:Rule
    val composeRule = createComposeRule()
    @Test
    fun warranty_item_displays_title_and_description() {
        val item =
            WarrantyModel(
                localId = 1,
                remoteId = null,
                title = "Laptop",
                description = "MSI",
                expirationDate = System.currentTimeMillis(),
                imagePath = null,
                createdAt = System.currentTimeMillis(),
                syncStatus = SyncStatus.SYNCED
            )
        composeRule.setContent {
            WarrantyItem(
                item = item,
                onClick = {}
            )
        }
        composeRule
            .onNodeWithText("Laptop")
            .assertExists()
        composeRule
            .onNodeWithText("MSI")
            .assertExists()
    }
}