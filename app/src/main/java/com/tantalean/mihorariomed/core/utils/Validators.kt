package com.tantalean.mihorariomed.core.utils

object Validators {

    fun required(value: String, fieldName: String): String? {
        return if (value.trim().isEmpty()) "$fieldName es obligatorio." else null
    }

    // Formato simple HH:mm (24h)
    fun timeHHmm(value: String, fieldName: String): String? {
        val v = value.trim()
        val regex = Regex("^([01]\\d|2[0-3]):([0-5]\\d)$")
        return if (!regex.matches(v)) "$fieldName debe ser HH:mm (Ej: 08:30)." else null
    }
}
