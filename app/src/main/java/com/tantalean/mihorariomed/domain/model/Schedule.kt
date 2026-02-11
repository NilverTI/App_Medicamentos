package com.tantalean.mihorariomed.domain.model

data class Schedule(
    val id: Long,
    val nombre: String,
    val dosis: String,
    val hora: String,
    val frecuencia: String,
    val notas: String,
    val activo: Boolean
)
