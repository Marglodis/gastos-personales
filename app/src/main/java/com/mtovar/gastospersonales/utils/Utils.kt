package com.mtovar.gastospersonales.utils

import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(amount)
}

fun resetForm(
    onNombre: () -> Unit,
    onCategoria: () -> Unit,
    onMonto: () -> Unit,
    onNombreError: () -> Unit,
    onCategoriaError: () -> Unit,
    onMontoError: () -> Unit
) {
    onNombre()
    onCategoria()
    onMonto()
    onNombreError()
    onCategoriaError()
    onMontoError()
}