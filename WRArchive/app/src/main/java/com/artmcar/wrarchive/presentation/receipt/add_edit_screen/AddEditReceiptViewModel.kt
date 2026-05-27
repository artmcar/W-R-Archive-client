package com.artmcar.wrarchive.presentation.receipt.add_edit_screen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.domain.usecase.receipt_uc.AddReceiptUseCase
import com.artmcar.wrarchive.domain.usecase.receipt_uc.GetReceiptByIdUseCase
import com.artmcar.wrarchive.domain.usecase.receipt_uc.UpdateReceiptUseCase
import com.artmcar.wrarchive.domain.validation.ValidateDate
import com.artmcar.wrarchive.domain.validation.ValidateTitle
import com.artmcar.wrarchive.presentation.navigation.AddEditReceiptRoute
import com.artmcar.wrarchive.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddEditReceiptViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addReceiptUseCase: AddReceiptUseCase,
    private val updateReceiptUseCase: UpdateReceiptUseCase,
    private val getReceiptByIdUseCase: GetReceiptByIdUseCase,
    private val validateTitle: ValidateTitle,
    private val validateDate: ValidateDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditReceiptUiState())
    val uiState: StateFlow<AddEditReceiptUiState> = _uiState.asStateFlow()
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()
    private val route = savedStateHandle.toRoute<AddEditReceiptRoute>()
    init {
        route.receiptId?.let {
            loadReceipt(it)
        }
    }
    private fun loadReceipt(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val item = getReceiptByIdUseCase(id)
            item?.let {
                val formatter =
                    SimpleDateFormat(
                        "dd.MM.yyyy",
                        Locale.getDefault()
                    )
                _uiState.update { state ->
                    state.copy(
                        localId = it.localId,
                        title = it.title,
                        description = it.description,
                        purchaseDate = formatter.format(Date(it.purchaseDate)),
                        imageUri = it.imagePath?.let(Uri::parse),
                        createdAt = it.createdAt,
                        isEditMode = true,
                        isLoading = false
                    )
                }
            }
        }
    }
    fun updateTitle(value: String) {
        _uiState.update {
            it.copy(title = value)
        }
    }
    fun updateDescription(value: String) {
        _uiState.update {
            it.copy(description = value)
        }
    }
    fun updateDate(value: String) {
        _uiState.update {
            it.copy(purchaseDate = value)
        }
    }
    fun updateImage(uri: Uri?) {
        _uiState.update {
            it.copy(imageUri = uri)
        }
    }
    fun saveReceipt(
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val titleResult = validateTitle(currentState.title)
            val dateResult = validateDate(currentState.purchaseDate)
            val hasError = listOf(titleResult, dateResult).any{
                !it.isSuccessful
            }
            if(hasError){
                val errorMessageRes = listOf(
                    titleResult.errorMessageRes,
                    dateResult.errorMessageRes
                ).firstOrNull{
                    it != null
                }
                eventChannel.send(
                    UiEvent.ShowSnackbar(
                        errorMessageRes?: R.string.unknown_error
                    )
                )
                return@launch
            }
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val parsedDate = formatter.parse(currentState.purchaseDate)?.time ?: System.currentTimeMillis()
            val model = ReceiptModel(
                localId = currentState.localId,
                remoteId = null,
                title = currentState.title,
                description = currentState.description,
                purchaseDate = parsedDate,
                imagePath = currentState.imageUri?.toString(),
                createdAt =
                    if(currentState.isEditMode){
                        currentState.createdAt
                    }
                    else{
                        System.currentTimeMillis()
                    },
                syncStatus =
                    if(currentState.isEditMode) {
                        SyncStatus.UPDATED
                    }
                    else{
                        SyncStatus.CREATED
                    }
            )
            if(currentState.isEditMode) {
                updateReceiptUseCase(model)

            }
            else {
                addReceiptUseCase(model)
            }
            onSaved()
        }
    }
}