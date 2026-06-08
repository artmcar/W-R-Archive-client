package com.artmcar.wrarchive.presentation.receipt

import com.artmcar.wrarchive.domain.model.ReceiptModel

data class ReceiptUiState(
    val receipts: List<ReceiptModel> = emptyList(),
    val isSortedAscending: Boolean = true,
    val searchQuery: String = "",
    val allReceiptsCount: Int = 0
)