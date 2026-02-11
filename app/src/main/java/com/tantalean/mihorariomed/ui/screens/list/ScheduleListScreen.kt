package com.tantalean.mihorariomed.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import com.tantalean.mihorariomed.ui.components.LoadingErrorView
import com.tantalean.mihorariomed.ui.components.ScheduleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(
    onAdd: () -> Unit,
    onOpenDetail: (Long) -> Unit,
    vm: ScheduleListViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()

    // ✅ Refresca cuando vuelves desde detalle/form (para que cambie Activo/Inactivo)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                vm.load()
            }
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MiHorarioMed",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 20.dp) // ✅ aire
                    ) {
                        Text(
                            text = "Solo activos",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.width(10.dp))
                        Switch(
                            checked = state.onlyActives,
                            onCheckedChange = { vm.setOnlyActives(it) }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            // ✅ Ya no tapa toda la pantalla
            LoadingErrorView(
                isLoading = state.isLoading,
                error = state.error,
                onRetry = { vm.retry() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )

            if (!state.isLoading && state.error == null) {
                if (state.items.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay horarios para mostrar.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.items) { schedule ->
                            ScheduleItem(
                                schedule = schedule,
                                onClick = { onOpenDetail(schedule.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
