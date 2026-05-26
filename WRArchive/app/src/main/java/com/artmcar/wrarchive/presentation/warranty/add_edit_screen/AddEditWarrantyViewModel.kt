package com.artmcar.wrarchive.presentation.warranty.add_edit_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.presentation.warranty.AddWarrantyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditWarrantyViewModel @Inject constructor(
    private val addWarrantyUseCase: AddWarrantyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditWarrantyUiState())
    val uiState: StateFlow<AddEditWarrantyUiState> = _uiState.asStateFlow()
    fun updateTitle(value: String) {
        _uiState.update {
            it.copy(
                title = value
            )
        }
    }
    fun updateDescription(value: String) {
        _uiState.update {
            it.copy(
                description = value
            )
        }
    }
    fun updateImage(uri: Uri?) {
        _uiState.update {
            it.copy(
                imageUri = uri
            )
        }
    }
    fun saveWarranty(
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val currentState = _uiState.value
            addWarrantyUseCase(
                WarrantyModel(
                    localId = 0,
                    remoteId = null,
                    title = currentState.title,
                    description = currentState.description,
                    expirationDate = currentState.expirationDate,
                    imagePath = currentState.imageUri?.toString(),
                    createdAt = System.currentTimeMillis(),
                    syncStatus = SyncStatus.CREATED
                )
            )
            onSaved()
        }
    }
}