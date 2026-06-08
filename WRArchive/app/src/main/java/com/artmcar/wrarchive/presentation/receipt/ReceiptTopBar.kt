package com.artmcar.wrarchive.presentation.receipt

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import com.artmcar.wrarchive.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptTopBar(
    onProfileClick: () -> Unit,
    onSortClick: () -> Unit,
    isSortedAscending: Boolean
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
                val rotation by animateFloatAsState(
                    targetValue = if (isSortedAscending) 180f else 0f,
                    label = ""
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    )
}