package com.artmcar.wrarchive.presentation.warranty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.presentation.warranty.usecase.AddWarrantyUseCase
import com.artmcar.wrarchive.presentation.warranty.usecase.DeleteWarrantyUseCase
import com.artmcar.wrarchive.presentation.warranty.usecase.GetAllWarrantiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarrantyViewModel @Inject constructor(
    private val getAllWarrantiesUseCase: GetAllWarrantiesUseCase,
    private val addWarrantyUseCase: AddWarrantyUseCase,
    private val deleteWarrantyUseCase: DeleteWarrantyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(WarrantyUiState())
    val uiState: StateFlow<WarrantyUiState> = _uiState.asStateFlow()
    init {
        observeWarranties()
    }
    fun onEvent(event: WarrantyEvent) {
        when(event) {
            is WarrantyEvent.ShowAddDialog -> {
                _uiState.update {
                    it.copy(
                        isAddDialogVisible = true
                    )
                }
            }
            is WarrantyEvent.HideAddDialog -> {
                _uiState.update {
                    it.copy(
                        isAddDialogVisible = false
                    )
                }
            }
            is WarrantyEvent.ToggleSortOrder -> {
                _uiState.update {
                    it.copy(
                        isSortedAscending = !it.isSortedAscending
                    )
                }
                observeWarranties()
            }
            is WarrantyEvent.AddWarranty -> {
                addWarranty(
                    title = event.title,
                    description = event.description,
                    expirationDate = event.expirationDate
                )
            }
            is WarrantyEvent.DeleteWarranty -> {
                deleteWarranty(event.item)
            }
        }
    }
    private fun observeWarranties() {
        viewModelScope.launch {
            getAllWarrantiesUseCase().collect { warrantiesList ->
                val sortedList = if(
                    _uiState.value.isSortedAscending
                ) {
                    warrantiesList.sortedBy {
                        it.expirationDate
                    }
                } else {
                    warrantiesList.sortedByDescending {
                        it.expirationDate
                    }
                }
                _uiState.update {
                    it.copy(
                        warranties = sortedList
                    )
                }
            }
        }
    }
    private fun addWarranty(
        title: String,
        description: String,
        expirationDate: Long
    ) {
        viewModelScope.launch {
            addWarrantyUseCase(
                WarrantyModel(
                    localId = 0,
                    remoteId = null,
                    title = title,
                    description = description,
                    expirationDate = expirationDate,
                    imagePath = null,
                    createdAt = System.currentTimeMillis(),
                    syncStatus = SyncStatus.CREATED
                )
            )
            _uiState.update {
                it.copy(
                    isAddDialogVisible = false
                )
            }
        }
    }
    private fun deleteWarranty(
        item: WarrantyModel
    ) {
        viewModelScope.launch {
            deleteWarrantyUseCase(item)
        }
    }
}