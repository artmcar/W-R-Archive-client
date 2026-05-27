package com.artmcar.wrarchive.presentation.util

sealed interface UiEvent {
    data class ShowSnackbar(val messageRes: Int) : UiEvent
}