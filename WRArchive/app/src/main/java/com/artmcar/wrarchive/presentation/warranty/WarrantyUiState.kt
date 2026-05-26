package com.artmcar.wrarchive.presentation.warranty

import com.artmcar.wrarchive.domain.model.WarrantyModel

data class WarrantyUiState(
    val warranties: List<WarrantyModel> = emptyList(),
    val isLoading: Boolean = false,
    val isSortedAscending: Boolean = true
)