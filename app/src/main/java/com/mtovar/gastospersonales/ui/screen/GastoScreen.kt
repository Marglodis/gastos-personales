package com.mtovar.gastospersonales.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtovar.gastospersonales.model.Gasto
import com.mtovar.gastospersonales.ui.components.GastoItem
import com.mtovar.gastospersonales.viewmodel.GastoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoScreen(viewModel: GastoViewModel) {
    val gastos by viewModel.gastos.collectAsState()
    var nuevoGasto by remember { mutableStateOf(Gasto(nombre = "", categoria = "", monto = 0.0)) }
    var showInput by remember { mutableStateOf(false) }


    Scaffold(
        topBar = { TopAppBar(title = { Text("Gastos Personales") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showInput = !showInput },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(10),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Gasto",
                    modifier = Modifier.size(32.dp)
                )
            }
            if (showInput) {
                AlertDialog(
                    onDismissRequest = { showInput = false },
                    title = { Text("Nuevo Gasto") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = nuevoGasto.nombre,
                                onValueChange = {
                                    nuevoGasto = nuevoGasto.copy(nombre = it)
                                },
                                label = { Text("Nombre") })

                            OutlinedTextField(
                                value = nuevoGasto.categoria,
                                onValueChange = {
                                    nuevoGasto = nuevoGasto.copy(categoria = it)
                                },
                                label = { Text("Categoria") }
                            )

                            OutlinedTextField(
                                value = nuevoGasto.monto.toString(),
                                onValueChange = {
                                    nuevoGasto = nuevoGasto.copy(monto = it.toDoubleOrNull() ?: 0.0)
                                },
                                label = { Text("Monto") }
                            )
                        }

                    },
                    confirmButton = {
                        Button(onClick = {
                            if (nuevoGasto.nombre.isNotBlank()
                                && nuevoGasto.categoria.isNotBlank()
                                && nuevoGasto.monto.toString().isNotBlank()
                            ) {
                                viewModel.agregarGasto(nuevoGasto)
                                showInput = false
                            }
                        }) { Text("Agregar") }
                    },
                    dismissButton = {
                        Button(onClick = { showInput = false }) { Text("Cancelar") }
                    })

            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(gastos) { gasto ->
                GastoItem(gasto)
            }
        }
    }


}