package com.artmcar.wrarchive.presentation.receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artmcar.wrarchive.presentation.warranty_and_receipt.SwipeToDeleteContainer

@Composable
fun ReceiptScreen(
    onProfileClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: ReceiptViewModel =
        hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            ReceiptTopBar(
                onProfileClick = onProfileClick,
                onSortClick = {
                    viewModel.onEvent(
                        ReceiptEvent.ToggleSortOrder
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if(uiState.receipts.isEmpty()) {
                EmptyReceiptScreen()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.receipts,
                        key = { it.localId }
                    ) { item ->
                        SwipeToDeleteContainer(
                            item = item,
                            onDelete = {
                                viewModel.onEvent(
                                    ReceiptEvent
                                        .DeleteReceipt(it)
                                )
                            }
                        ) {
                            ReceiptItem(
                                item = item,
                                onClick = {
                                    onEditClick(
                                        item.localId
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}