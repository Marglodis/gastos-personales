package com.mtovar.gastospersonales.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import com.mtovar.gastospersonales.viewmodel.GastoViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtovar.gastospersonales.model.Gasto
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.mtovar.gastospersonales.ui.components.GastoItem


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
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(gastos) { gasto ->
                GastoItem(gasto)
            }
        }
    }


}