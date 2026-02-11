package com.tantalean.mihorariomed.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tantalean.mihorariomed.core.result.Resource
import com.tantalean.mihorariomed.core.utils.ServiceLocator
import com.tantalean.mihorariomed.domain.model.Schedule
import com.tantalean.mihorariomed.domain.usecase.GetSchedulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleListViewModel(
    private val getSchedulesUseCase: GetSchedulesUseCase
) : ViewModel() {

    // ✅ Constructor vacío para viewModel() sin DI
    constructor() : this(
        getSchedulesUseCase = ServiceLocator.getSchedulesUseCase()
    )

    private val _uiState = MutableStateFlow(ScheduleListUiState())
    val uiState: StateFlow<ScheduleListUiState> = _uiState.asStateFlow()

    private var cachedAll: List<Schedule> = emptyList()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getSchedulesUseCase()) {
                is Resource.Success -> {
                    cachedAll = result.data
                    applyFilterAndPublish()
                    _uiState.update { it.copy(isLoading = false, error = null) }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Error al listar horarios."
                        )
                    }
                }

                Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
            }
        }
    }

    fun retry() = load()

    fun setOnlyActives(value: Boolean) {
        _uiState.update { it.copy(onlyActives = value) }
        applyFilterAndPublish()
    }

    private fun applyFilterAndPublish() {
        val only = _uiState.value.onlyActives
        val filtered = if (only) cachedAll.filter { it.activo } else cachedAll
        _uiState.update { it.copy(items = filtered) }
    }
}
