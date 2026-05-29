package com.artmcar.wrarchive.presentation.warranty.add_edit_screen

import android.net.Uri

data class AddEditWarrantyUiState(
    val localId: Int = 0,
    val title: String = "",
    val description: String = "",
    val expirationDate: String = "",
    val imageUri: Uri? = null,
    val createdAt: Long = 0L,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val imagePath: String? = null
)