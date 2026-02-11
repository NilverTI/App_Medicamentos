package com.tantalean.mihorariomed.ui.screens.list

import com.tantalean.mihorariomed.domain.model.Schedule

data class ScheduleListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val onlyActives: Boolean = false,
    val items: List<Schedule> = emptyList()
)
