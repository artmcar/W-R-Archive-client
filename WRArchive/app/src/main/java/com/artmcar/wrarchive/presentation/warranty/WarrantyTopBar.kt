package com.artmcar.wrarchive.presentation.warranty

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artmcar.wrarchive.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarrantyTopBar(
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
            Text(stringResource(R.string.warranties))
        },
        actions = {
            IconButton(
                onClick = onSortClick
            ) {
                Icon(
                    //Todo подобрать иконку для сортировки
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    )
}