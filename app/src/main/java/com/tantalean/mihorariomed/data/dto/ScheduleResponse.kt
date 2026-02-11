package com.tantalean.mihorariomed.data.dto

data class ScheduleResponse(
    val id: Long,
    val nombre: String,
    val dosis: String,
    val hora: String,
    val frecuencia: String,
    val notas: String,
    val activo: Boolean
)
