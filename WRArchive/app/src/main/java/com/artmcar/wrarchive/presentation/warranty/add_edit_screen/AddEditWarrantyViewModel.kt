package com.artmcar.wrarchive.presentation.warranty.add_edit_screen

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.usecase.warranty_uc.AddWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.GetWarrantyByIdUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.UpdateWarrantyUseCase
import com.artmcar.wrarchive.domain.validation.ValidateDate
import com.artmcar.wrarchive.domain.validation.ValidateTitle
import com.artmcar.wrarchive.presentation.navigation.AddEditWarrantyRoute
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
class AddEditWarrantyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addWarrantyUseCase: AddWarrantyUseCase,
    private val updateWarrantyUseCase: UpdateWarrantyUseCase,
    private val getWarrantyByIdUseCase: GetWarrantyByIdUseCase,
    private val validateTitle: ValidateTitle,
    private val validateDate: ValidateDate
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditWarrantyUiState())
    val uiState: StateFlow<AddEditWarrantyUiState> = _uiState.asStateFlow()
    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    private val route = savedStateHandle.toRoute<AddEditWarrantyRoute>()
    init{
        route.warrantyId?.let{
            loadWarranty(it)
        }
    }
    private fun loadWarranty(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val item = getWarrantyByIdUseCase(id)
            item?.let {
                val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                _uiState.update { state ->
                    state.copy(
                        localId = it.localId,
                        title = it.title,
                        description = it.description,
                        expirationDate =
                            formatter.format(
                                Date(
                                    it.expirationDate
                                )
                            ),
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
            val titleResult = validateTitle(currentState.title)
            val dateResult = validateDate(currentState.expirationDate)
            val hasError = listOf(titleResult, dateResult).any {
                !it.isSuccessful
            }
            if(hasError) {
                val errorMessageRes = listOf(
                    titleResult.errorMessageRes,
                    dateResult.errorMessageRes
                ).firstOrNull {
                    it != null
                }
                eventChannel.send(
                    UiEvent.ShowSnackbar(
                        errorMessageRes ?: R.string.unknown_error
                    )
                )
                return@launch
            }
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val parsedDate = formatter.parse(currentState.expirationDate)?.time ?: System.currentTimeMillis()
            val model = WarrantyModel(
                localId = currentState.localId,
                remoteId = null,
                title = currentState.title,
                description = currentState.description,
                expirationDate = parsedDate,
                imagePath = currentState.imageUri?.toString(),
                createdAt =
                    if(currentState.isEditMode){
                        currentState.createdAt
                    }
                    else{
                        System.currentTimeMillis()
                    },
                syncStatus =
                    if(currentState.isEditMode){
                        SyncStatus.UPDATED
                    }
                    else{
                        SyncStatus.CREATED
                    }
            )
            if(currentState.isEditMode) {
                updateWarrantyUseCase(model)
            }
            else {
                addWarrantyUseCase(model)
            }
            onSaved()
        }
    }
    fun updateDate(value: String) {
        _uiState.update {
            it.copy(
                expirationDate = value
            )
        }
    }
}
