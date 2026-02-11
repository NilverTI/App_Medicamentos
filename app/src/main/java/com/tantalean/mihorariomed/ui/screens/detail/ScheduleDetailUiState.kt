package com.tantalean.mihorariomed.ui.screens.detail

import com.tantalean.mihorariomed.domain.model.Schedule

data class ScheduleDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val schedule: Schedule? = null,
    val deletedOk: Boolean = false
)
