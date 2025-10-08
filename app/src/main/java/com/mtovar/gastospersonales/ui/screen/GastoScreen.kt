package com.mtovar.gastospersonales.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mtovar.gastospersonales.model.Gasto
import com.mtovar.gastospersonales.ui.components.EmptyGastosView
import com.mtovar.gastospersonales.ui.components.GastoItem
import com.mtovar.gastospersonales.ui.components.GastosSummaryCard
import com.mtovar.gastospersonales.utils.formatCurrency
import com.mtovar.gastospersonales.utils.resetForm
import com.mtovar.gastospersonales.viewmodel.GastoViewModel
import java.text.NumberFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastoScreen(viewModel: GastoViewModel) {
    val gastos by viewModel.gastos.collectAsState()
    var showInput by remember { mutableStateOf(false) }

    // Separar variables para mejor manjejo
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var montoText by remember { mutableStateOf("") }

    // Errores de validación
    var nombreError by remember { mutableStateOf(false) }
    var categoriaError by remember { mutableStateOf(false) }
    var montoError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gastos Personales") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showInput = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Gasto",
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Resumen de gastos
            if (gastos.isNotEmpty()) {
                GastosSummaryCard(totalGastos = gastos.sumOf { it.monto })
            }

            // Lista de gastos
            if (gastos.isEmpty()) {
                EmptyGastosView()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(gastos, key = { it.id }) { gasto ->
                        GastoItem(
                            gasto = gasto,
                            onDelete = { viewModel.eliminarGasto(gasto) }
                        )
                    }
                }
            }
        }
    }

    // Dialog para agregar gasto
    if (showInput) {
        AlertDialog(
            onDismissRequest = {
                showInput = false
                resetForm(
                    onNombre = { nombre = "" },
                    onCategoria = { categoria = "" },
                    onMonto = { montoText = "" },
                    onNombreError = { nombreError = false },
                    onCategoriaError = { categoriaError = false },
                    onMontoError = { montoError = false }
                )
            },
            title = {
                Text(
                    "Nuevo Gasto",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            nombreError = false
                        },
                        label = { Text("Nombre del gasto") },
                        isError = nombreError,
                        supportingText = {
                            if (nombreError) Text("El nombre es requerido")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = categoria,
                        onValueChange = {
                            categoria = it
                            categoriaError = false
                        },
                        label = { Text("Categoría") },
                        isError = categoriaError,
                        supportingText = {
                            if (categoriaError) Text("La categoría es requerida")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = montoText,
                        onValueChange = {
                            // Solo permitir números y punto decimal
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                montoText = it
                                montoError = false
                            }
                        },
                        label = { Text("Monto") },
                        isError = montoError,
                        supportingText = {
                            if (montoError) Text("Ingrese un monto válido mayor a 0")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        prefix = { Text("$") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Validación
                        val isNombreValid = nombre.isNotBlank()
                        val isCategoriaValid = categoria.isNotBlank()
                        val monto = montoText.toDoubleOrNull() ?: 0.0
                        val isMontoValid = monto > 0

                        nombreError = !isNombreValid
                        categoriaError = !isCategoriaValid
                        montoError = !isMontoValid

                        if (isNombreValid && isCategoriaValid && isMontoValid) {
                            viewModel.agregarGasto(
                                Gasto(
                                    nombre = nombre.trim(),
                                    categoria = categoria.trim(),
                                    monto = monto
                                )
                            )
                            showInput = false
                            // Limpiar formulario
                            nombre = ""
                            categoria = ""
                            montoText = ""
                        }
                    }
                ) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showInput = false
                        nombre = ""
                        categoria = ""
                        montoText = ""
                        nombreError = false
                        categoriaError = false
                        montoError = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
