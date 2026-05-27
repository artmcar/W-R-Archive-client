package com.artmcar.wrarchive.presentation.receipt

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artmcar.wrarchive.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptTopBar(
    onProfileClick: () -> Unit,
    onSortClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onProfileClick
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(stringResource(R.string.receipts))
        },
        actions = {
            IconButton(
                onClick = onSortClick
            ) {
                Icon(
                    //TODO подобрать иконку для сортировки
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }
    )
}