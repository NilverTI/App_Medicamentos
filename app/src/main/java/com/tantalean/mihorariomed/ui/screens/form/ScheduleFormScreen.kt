package com.tantalean.mihorariomed.ui.screens.form

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tantalean.mihorariomed.ui.components.LoadingErrorView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleFormScreen(
    scheduleId: Long?,
    onBack: () -> Unit,
    onSaved: (Long) -> Unit, // ✅ NUEVO
    vm: ScheduleFormViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isEdit = scheduleId != null

    LaunchedEffect(scheduleId) {
        vm.loadIfEdit(scheduleId)
    }

    // ✅ cuando se guarda/actualiza -> snackbar + navegar
    LaunchedEffect(state.savedId) {
        val id = state.savedId
        if (id != null) {
            scope.launch {
                snackbarHostState.showSnackbar(state.successMessage ?: "Guardado ✅")
            }
            vm.consumeSaved()
            onSaved(id)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Editar horario" else "Nuevo horario") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Volver") }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LoadingErrorView(
                isLoading = state.isLoading,
                error = state.error,
                onRetry = { vm.loadIfEdit(scheduleId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = state.nombre,
                    onValueChange = vm::onNombre,
                    label = { Text("Nombre del medicamento") },
                    isError = state.nombreError != null,
                    supportingText = { if (state.nombreError != null) Text(state.nombreError!!) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.dosis,
                    onValueChange = vm::onDosis,
                    label = { Text("Dosis (ej: 500mg, 1 tableta)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.hora,
                    onValueChange = vm::onHora,
                    label = { Text("Hora (HH:mm)") },
                    isError = state.horaError != null,
                    supportingText = { if (state.horaError != null) Text(state.horaError!!) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.frecuencia,
                    onValueChange = vm::onFrecuencia,
                    label = { Text("Frecuencia (ej: Diaria, cada 8 horas)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.notas,
                    onValueChange = vm::onNotas,
                    label = { Text("Notas") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Activo", style = MaterialTheme.typography.titleMedium)
                    Switch(checked = state.activo, onCheckedChange = vm::onActivo)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = vm::save,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !state.isLoading
                ) {
                    Text(
                        text = if (isEdit) "Actualizar" else "Guardar",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
