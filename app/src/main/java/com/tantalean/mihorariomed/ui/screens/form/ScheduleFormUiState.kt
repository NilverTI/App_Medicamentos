package com.tantalean.mihorariomed.ui.screens.form

data class ScheduleFormUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,

    val id: Long? = null,

    val nombre: String = "",
    val dosis: String = "",
    val hora: String = "",
    val frecuencia: String = "",
    val notas: String = "",
    val activo: Boolean = true,

    val nombreError: String? = null,
    val horaError: String? = null,

    // ✅ Para navegar luego del guardado/actualizado
    val savedId: Long? = null
)
