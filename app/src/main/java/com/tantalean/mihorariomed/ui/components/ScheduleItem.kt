package com.tantalean.mihorariomed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tantalean.mihorariomed.domain.model.Schedule

@Composable
fun ScheduleItem(
    schedule: Schedule,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(schedule.nombre, style = MaterialTheme.typography.titleMedium)

                AssistChip(
                    onClick = {},
                    label = { Text(if (schedule.activo) "Activo" else "Inactivo") }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("⏰ ${schedule.hora} • ${schedule.frecuencia}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text("💊 ${schedule.dosis}", style = MaterialTheme.typography.bodySmall)

            if (schedule.notas.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text("📝 ${schedule.notas}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
