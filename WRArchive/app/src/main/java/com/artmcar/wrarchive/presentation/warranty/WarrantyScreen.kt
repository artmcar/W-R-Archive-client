package com.artmcar.wrarchive.presentation.warranty

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


@Composable
fun WarrantyScreen(
    onProfileClick: () -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: WarrantyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            WarrantyTopBar(
                onProfileClick = onProfileClick,
                onSortClick = {
                    viewModel.onEvent(
                        WarrantyEvent.ToggleSortOrder
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ){
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
            if(uiState.warranties.isEmpty()) {

                EmptyWarrantyScreen()
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = uiState.warranties,
                        key = {it.localId}
                        ) { item ->
                        SwipeToDeleteContainer(
                            item = item,
                            onDelete = {
                                viewModel.onEvent(
                                    WarrantyEvent.DeleteWarranty(item)
                                )
                            }
                        ) {
                            WarrantyItem(
                                item = item,
                                onClick = {
                                    onEditClick(item.localId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}