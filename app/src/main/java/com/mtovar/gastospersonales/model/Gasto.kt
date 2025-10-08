package com.mtovar.gastospersonales.model

data class Gasto(
    val id: Long = System.currentTimeMillis(),
    val nombre: String,
    val categoria: String,
    val monto: Double
)
