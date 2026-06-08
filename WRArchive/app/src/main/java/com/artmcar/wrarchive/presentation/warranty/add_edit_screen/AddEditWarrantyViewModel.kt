package com.artmcar.wrarchive.presentation.warranty.add_edit_screen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.data.local.ImageStorageManager
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.data.remote.api.ApiConfig
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.domain.repository.SyncRepository
import com.artmcar.wrarchive.domain.usecase.warranty_uc.AddWarrantyUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.GetWarrantyByIdUseCase
import com.artmcar.wrarchive.domain.usecase.warranty_uc.UpdateWarrantyUseCase
import com.artmcar.wrarchive.domain.validation.ValidateDate
import com.artmcar.wrarchive.domain.validation.ValidateTitle
import com.artmcar.wrarchive.presentation.navigation.AddEditWarrantyRoute
import com.artmcar.wrarchive.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
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
    private val validateDate: ValidateDate,
    private val imageStorageManager: ImageStorageManager,
    private val syncRepository: SyncRepository
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
                        imageUri =
                            it.imagePath?.let { path ->
                                if(path.startsWith("/uploads")) {
                                    Uri.parse("${ApiConfig.BASE_URL}$path")
                                } else {
                                    Uri.fromFile(File(path))
                                }
                            },
                        imagePath = it.imagePath,
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
        if(uri == null) return
        val savedPath = imageStorageManager.saveImageFromUri(uri)
        _uiState.update {
            it.copy(imageUri = Uri.fromFile(File(savedPath)), imagePath = savedPath)
        }
    }
    fun saveWarranty() {
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
            val existingItem = currentState.localId?.let {getWarrantyByIdUseCase(it)}
            val syncStatus = when {
                !currentState.isEditMode -> {
                    SyncStatus.CREATED
                }
                existingItem?.remoteId != null -> {
                    SyncStatus.UPDATED
                }
                else -> {
                    SyncStatus.CREATED
                }
            }
            val model = WarrantyModel(
                localId = currentState.localId,
                remoteId = existingItem?.remoteId,
                title = currentState.title,
                description = currentState.description,
                expirationDate = parsedDate,
                imagePath = currentState.imagePath,
                createdAt =
                    if(currentState.isEditMode){
                        currentState.createdAt
                    }
                    else{
                        System.currentTimeMillis()
                    },
                syncStatus = syncStatus
            )
            if(currentState.isEditMode) {
                updateWarrantyUseCase(model)
            }
            else {
                addWarrantyUseCase(model)
            }
            launch(Dispatchers.IO) {
                syncRepository.syncPendingData()
            }
            eventChannel.send(UiEvent.NavigateBack)
        }
    }
    fun updateDate(value: String) {
        _uiState.update {
            it.copy(
                expirationDate = value
            )
        }
    }
    fun saveImageToGallery(
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uri = _uiState.value.imageUri ?: return@launch
                val bitmap =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                val fileName = "Warranty_${System.currentTimeMillis()}.jpg"
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES
                    )
                }
                val savedUri = context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                savedUri?.let {
                    context.contentResolver
                        .openOutputStream(it)
                        ?.use { output ->
                            bitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                100,
                                output
                            )
                        }
                }
                eventChannel.send( UiEvent.ShowSnackbar(R.string.document_saved) )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
