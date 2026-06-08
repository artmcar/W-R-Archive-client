package com.artmcar.wrarchive.presentation.warranty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.presentation.warranty_and_receipt.SwipeToDeleteContainer


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
            Column (
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                WarrantySearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = {
                        viewModel.onEvent(
                            WarrantyEvent.SearchChanged(it)
                        )
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                when {
                    uiState.allWarrantiesCount == 0 -> {
                        EmptyWarrantyScreen()
                    }
                    uiState.warranties.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text( text = stringResource(R.string.nothing_found))
                        }
                    }
                    else -> {
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
                                            WarrantyEvent.DeleteWarranty(it)
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
    }
}