package com.artmcar.wrarchive.presentation.warranty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.usecase.warranty_uc.AddWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.DeleteWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.GetAllWarrantiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarrantyViewModel @Inject constructor(
    private val getAllWarrantiesUseCase: GetAllWarrantiesUseCase,
    private val deleteWarrantyUseCase: DeleteWarrantyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(WarrantyUiState())
    val uiState: StateFlow<WarrantyUiState> = _uiState.asStateFlow()
    init {
        observeWarranties()
    }
    fun onEvent(event: WarrantyEvent) {
        when(event) {
            is WarrantyEvent.ToggleSortOrder -> {
                _uiState.update {
                    it.copy(
                        isSortedAscending = !it.isSortedAscending
                    )
                }
                observeWarranties()
            }
            is WarrantyEvent.DeleteWarranty -> {
                deleteWarranty(event.item)
            }
        }
    }
    private fun observeWarranties() {
        viewModelScope.launch {
            getAllWarrantiesUseCase().collectLatest { warrantiesList ->
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
    private fun deleteWarranty(
        item: WarrantyModel
    ) {
        viewModelScope.launch {
            deleteWarrantyUseCase(item)
        }
    }
}