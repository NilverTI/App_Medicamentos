package com.tantalean.mihorariomed.data.dto

data class ScheduleRequest(
    val nombre: String,
    val dosis: String,
    val hora: String,
    val frecuencia: String,
    val notas: String,
    val activo: Boolean
)
