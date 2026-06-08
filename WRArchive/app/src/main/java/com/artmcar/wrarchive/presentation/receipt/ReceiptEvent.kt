package com.artmcar.wrarchive.presentation.receipt

import com.artmcar.wrarchive.domain.model.ReceiptModel

sealed interface ReceiptEvent {
    data object ToggleSortOrder : ReceiptEvent
    data class DeleteReceipt(val item: ReceiptModel) : ReceiptEvent
    data class SearchChanged(val query: String): ReceiptEvent
}