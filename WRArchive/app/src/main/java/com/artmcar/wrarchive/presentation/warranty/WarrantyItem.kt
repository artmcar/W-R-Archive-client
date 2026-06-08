package com.artmcar.wrarchive.presentation.warranty

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.data.local.room.SyncStatus
import com.artmcar.wrarchive.domain.model.WarrantyModel
import com.artmcar.wrarchive.presentation.warranty_and_receipt.SyncStatusChip
import com.artmcar.wrarchive.ui.theme.ExtendedTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WarrantyItem(
    item: WarrantyModel,
    onClick: () -> Unit
) {
    val formatter = remember {
        SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.getDefault()
        )
    }
    val currentTime = System.currentTimeMillis()
        val totalDays = ((item.expirationDate - item.createdAt) / (1000L * 60L * 60L * 24L))
            .coerceAtLeast(1)
    val remainingDays = ((item.expirationDate - currentTime) / (1000L * 60L * 60L * 24L))
        .coerceAtLeast(0)

    val progress = (remainingDays.toFloat() / totalDays.toFloat()).coerceIn(0f, 1f)
    val progressColor = when {
        progress > 0.5f -> Color(0xFF4CAF50)
        progress > 0.25f -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
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
                    R.string.expires,
                    formatter.format(
                        Date(item.expirationDate)
                    )
                ),
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .background(progressColor)
                )
                Text(
                    text =
                        if(remainingDays <= 0) {
                            stringResource(R.string.warranty_expired)
                        } else {
                            "$remainingDays/$totalDays"
                        },
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium,
                    color =
                        if(remainingDays <= 0) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                )
            }
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