package com.artmcar.wrarchive.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val messageRes: Int) : UiEvent()
    data object NavigateBack : UiEvent()
}