package com.tantalean.mihorariomed.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tantalean.mihorariomed.ui.components.LoadingErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDetailScreen(
    scheduleId: Long,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    viewModel: ScheduleDetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var confirmDelete by remember { mutableStateOf(false) }

    LaunchedEffect(scheduleId) {
        viewModel.load(scheduleId)
    }

    // Si se eliminó, vuelve atrás
    LaunchedEffect(state.deletedOk) {
        if (state.deletedOk) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            when {
                state.isLoading -> {
                    LoadingErrorView(isLoading = true, error = null, onRetry = { viewModel.load(scheduleId) })
                }

                state.error != null -> {
                    LoadingErrorView(isLoading = false, error = state.error, onRetry = { viewModel.load(scheduleId) })
                }

                state.schedule != null -> {
                    val s = state.schedule!!

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = s.nombre,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(Modifier.height(10.dp))

                            InfoRow(icon = Icons.Default.Vaccines, label = "Dosis", value = s.dosis)
                            InfoRow(icon = Icons.Default.AccessTime, label = "Hora", value = s.hora)
                            InfoRow(icon = Icons.Default.Repeat, label = "Frecuencia", value = s.frecuencia)
                            if (s.notas.isNotBlank()) {
                                InfoRow(icon = Icons.Default.Notes, label = "Notas", value = s.notas)
                            }

                            Spacer(Modifier.height(10.dp))

                            val statusText = if (s.activo) "Activo" else "Inactivo"
                            AssistChip(
                                onClick = {},
                                label = { Text("Estado: $statusText") },
                                colors = if (s.activo) {
                                    AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                } else {
                                    AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )

                            Spacer(Modifier.height(18.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                FilledTonalButton(
                                    onClick = onEdit,
                                    modifier = Modifier.weight(1f)
                                ) { Text("Editar") }

                                Button(
                                    onClick = { confirmDelete = true },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    )
                                ) { Text("Eliminar") }
                            }
                        }
                    }
                }
            }

            if (confirmDelete) {
                AlertDialog(
                    onDismissRequest = { confirmDelete = false },
                    title = { Text("Confirmar") },
                    text = { Text("¿Seguro que deseas eliminar este horario?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                confirmDelete = false
                                viewModel.delete(scheduleId)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text("Eliminar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { confirmDelete = false }) { Text("Cancelar") }
                    }
                )
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Column {
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
