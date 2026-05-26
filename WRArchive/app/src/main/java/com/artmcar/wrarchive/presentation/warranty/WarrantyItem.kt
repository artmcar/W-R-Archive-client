package com.artmcar.wrarchive.presentation.warranty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artmcar.wrarchive.R
import com.artmcar.wrarchive.domain.model.WarrantyModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WarrantyItem(
    item: WarrantyModel,
    onClick: () -> Unit
) {
    val formatter = SimpleDateFormat(
        "dd.MM.yyyy",
        Locale.getDefault()
    )
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = item.description
            )
            Text(
                text = "${R.string.expires}: ${
                    formatter.format(
                        Date(item.expirationDate)
                    )
                }"
            )
        }
    }
}