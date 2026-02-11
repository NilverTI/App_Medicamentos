package com.tantalean.mihorariomed.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tantalean.mihorariomed.core.result.Resource
import com.tantalean.mihorariomed.core.utils.ServiceLocator
import com.tantalean.mihorariomed.domain.usecase.DeleteScheduleUseCase
import com.tantalean.mihorariomed.domain.usecase.GetScheduleByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleDetailViewModel(
    private val getByIdUseCase: GetScheduleByIdUseCase,
    private val deleteUseCase: DeleteScheduleUseCase
) : ViewModel() {

    // ✅ Constructor vacío para que viewModel() no crashee
    constructor() : this(
        getByIdUseCase = ServiceLocator.getScheduleByIdUseCase(),
        deleteUseCase = ServiceLocator.deleteScheduleUseCase()
    )

    private val _uiState = MutableStateFlow(ScheduleDetailUiState())
    val uiState: StateFlow<ScheduleDetailUiState> = _uiState.asStateFlow()

    fun load(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, deletedOk = false) }

            when (val res = getByIdUseCase(id)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, schedule = res.data, error = null) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = res.message ?: "Error al obtener detalle.") }
                }
                is Resource.Loading -> Unit
            }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, deletedOk = false) }

            when (val res = deleteUseCase(id)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, deletedOk = true, error = null) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, deletedOk = false, error = res.message ?: "Error al eliminar.") }
                }
                is Resource.Loading -> Unit
            }
        }
    }
}
