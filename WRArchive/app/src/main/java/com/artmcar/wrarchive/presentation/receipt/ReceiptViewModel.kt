package com.artmcar.wrarchive.presentation.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.domain.repository.SyncRepository
import com.artmcar.wrarchive.domain.usecase.receipt_uc.DeleteReceiptUseCase
import com.artmcar.wrarchive.domain.usecase.receipt_uc.GetAllReceiptsUseCase
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
class ReceiptViewModel @Inject constructor(
    private val getAllReceiptsUseCase: GetAllReceiptsUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase,
    private val syncRepository: SyncRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReceiptUiState())
    val uiState: StateFlow<ReceiptUiState> = _uiState.asStateFlow()
    private var searchJob: Job? = null
    private var observeJob: Job? = null
    init {
        observeReceipts()
    }
    fun onEvent(event: ReceiptEvent) {
        when(event) {
            is ReceiptEvent.ToggleSortOrder -> {
                _uiState.update {
                    it.copy(
                        isSortedAscending = !it.isSortedAscending
                    )
                }
                observeReceipts()
            }
            is ReceiptEvent.DeleteReceipt -> {
                deleteReceipt(event.item)
            }
            is ReceiptEvent.SearchChanged -> {
                _uiState.update {
                    it.copy( searchQuery = event.query)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(300)
                    observeReceipts()
                }
            }
        }
    }
    private fun observeReceipts() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            getAllReceiptsUseCase().collectLatest { receiptsList ->
                val query = _uiState.value.searchQuery.trim().lowercase()
                val filteredList =
                    if(query.isBlank()){
                        receiptsList
                    }
                    else{
                        receiptsList.filter {
                            it.title.lowercase().contains(query) ||
                                    it.description.lowercase().contains(query)
                        }
                    }
                val sortedList = if(
                    _uiState.value.isSortedAscending
                ) {
                    filteredList.sortedBy {
                        it.purchaseDate
                    }
                } else {
                    filteredList.sortedByDescending {
                        it.purchaseDate
                    }
                }
                _uiState.update {
                    it.copy(
                        receipts = sortedList,
                        allReceiptsCount = receiptsList.size
                    )
                }
            }
        }
    }
    private fun deleteReceipt(
        item: ReceiptModel
    ) {
        viewModelScope.launch {
            deleteReceiptUseCase(item)
            try {
                syncRepository.syncPendingData()
                syncRepository.downloadRemoteData()
            }
            catch (_: Exception) { }
        }
    }
}