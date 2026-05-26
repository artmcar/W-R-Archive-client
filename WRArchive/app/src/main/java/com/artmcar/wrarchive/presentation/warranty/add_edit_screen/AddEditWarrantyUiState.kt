package com.artmcar.wrarchive.presentation.warranty.add_edit_screen

import android.net.Uri

data class AddEditWarrantyUiState(
    val title: String = "",
    val description: String = "",
    val expirationDate: String = "",
    val imageUri: Uri? = null,
    val isEditMode: Boolean = false
)