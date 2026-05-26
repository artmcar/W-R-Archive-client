package com.artmcar.wrarchive.presentation.warranty

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
fun WarrantyTopBar(
    onProfileClick: () -> Unit,
    onSortClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.warranty_label))
        },
        actions = {
            IconButton(
                onClick = onProfileClick
            ) {
                Icon(
                    //Todo подобрать иконку для сортировки
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = onProfileClick
            ) {
               Icon(
                   imageVector = Icons.Default.Person,
                   contentDescription = null
               )
            }
        }
    )
}