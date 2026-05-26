package com.artmcar.wrarchive.presentation.warranty

import com.artmcar.wrarchive.domain.model.WarrantyModel

data class WarrantyUiState(
    val warranties: List<WarrantyModel> = emptyList(),
    val isSortedAscending: Boolean = true
)