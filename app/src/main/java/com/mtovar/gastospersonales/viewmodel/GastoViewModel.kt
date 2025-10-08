package com.mtovar.gastospersonales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtovar.gastospersonales.model.Gasto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GastoViewModel : ViewModel() {
    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos = _gastos.asStateFlow()

    fun agregarGasto(gasto: Gasto) {
        viewModelScope.launch {
            _gastos.value = _gastos.value + gasto
        }
    }
}