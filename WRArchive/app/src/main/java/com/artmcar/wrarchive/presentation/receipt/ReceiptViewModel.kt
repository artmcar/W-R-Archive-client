package com.artmcar.wrarchive.presentation.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.presentation.receipt.usecase.DeleteReceiptUseCase
import com.artmcar.wrarchive.presentation.receipt.usecase.GetAllReceiptsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val getAllReceiptsUseCase: GetAllReceiptsUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReceiptUiState())
    val uiState: StateFlow<ReceiptUiState> = _uiState.asStateFlow()
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
        }
    }
    private fun observeReceipts() {
        viewModelScope.launch {
            getAllReceiptsUseCase().collect {
                val sortedList = if(_uiState.value.isSortedAscending) {
                    it.sortedBy { item ->
                        item.purchaseDate
                    }
                } else {
                    it.sortedByDescending { item ->
                        item.purchaseDate
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        receipts = sortedList
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
        }
    }
}