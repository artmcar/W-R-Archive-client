package com.artmcar.wrarchive.presentation.receipt


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.ReceiptModel
import com.artmcar.wrarchive.presentation.warranty_and_receipt.SyncStatusChip
import com.artmcar.wrarchive.ui.theme.ExtendedTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReceiptItem(
    item: ReceiptModel,
    onClick: () -> Unit
) {
    val formatter = remember {
        SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ExtendedTheme.colors.customCardBackgroundColors)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge
            )
            if(item.description.isNotBlank()) {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = stringResource(
                    R.string.purchase,
                    formatter.format(
                        Date(item.purchaseDate)
                    )
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            when(item.syncStatus) {
                SyncStatus.CREATED -> {
                    SyncStatusChip(
                        text = stringResource(
                            R.string.status_waiting_create
                        )
                    )
                }
                SyncStatus.UPDATED -> {
                    SyncStatusChip(
                        text = stringResource(
                            R.string.status_waiting_update
                        )
                    )
                }
                SyncStatus.DELETED -> {
                    SyncStatusChip(
                        text = stringResource(
                            R.string.status_waiting_delete
                        )
                    )
                }
                SyncStatus.SYNCED -> Unit
            }
        }
    }
}