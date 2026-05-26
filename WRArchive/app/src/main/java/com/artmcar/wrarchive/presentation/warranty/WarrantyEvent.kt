package com.artmcar.wrarchive.presentation.warranty

import com.artmcar.wrarchive.domain.model.WarrantyModel

sealed interface WarrantyEvent {
    data object ToggleSortOrder : WarrantyEvent
    data class DeleteWarranty(
        val item: WarrantyModel
    ) : WarrantyEvent
}