package com.artmcar.wrarchive.presentation.receipt

import com.artmcar.wrarchive.domain.model.ReceiptModel

data class ReceiptUiState(
    val receipts: List<ReceiptModel> = emptyList(),
    val isLoading: Boolean = false,
    val isSortedAscending: Boolean = true
)