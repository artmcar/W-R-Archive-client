package com.artmcar.wrarchive.presentation.warranty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.repository.SyncRepository
import com.artmcar.wrarchive.domain.usecase.warranty_uc.DeleteWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.GetAllWarrantiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private val deleteWarrantyUseCase: DeleteWarrantyUseCase,
    private val syncRepository: SyncRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(WarrantyUiState())
    val uiState: StateFlow<WarrantyUiState> = _uiState.asStateFlow()
    private var searchJob: Job? = null
    private var observeJob: Job? = null
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
            is WarrantyEvent.SearchChanged -> {
                _uiState.update {
                    it.copy( searchQuery = event.query)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(300)
                    observeWarranties()
                }
            }
        }
    }
    private fun observeWarranties() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            getAllWarrantiesUseCase().collectLatest { warrantiesList ->
                val query = _uiState.value.searchQuery.trim().lowercase()
                val filteredList =
                    if(query.isBlank()){
                        warrantiesList
                    }
                    else{
                        warrantiesList.filter {
                            it.title.lowercase().contains(query) ||
                                    it.description.lowercase().contains(query)
                        }
                    }
                val sortedList = if(
                    _uiState.value.isSortedAscending
                ) {
                    filteredList.sortedBy {
                        it.expirationDate
                    }
                } else {
                    filteredList.sortedByDescending {
                        it.expirationDate
                    }
                }
                _uiState.update {
                    it.copy(
                        warranties = sortedList,
                        allWarrantiesCount = warrantiesList.size
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
            try {
                syncRepository.syncPendingData()
                syncRepository.downloadRemoteData()
            }
            catch (_: Exception) { }
        }
    }
}