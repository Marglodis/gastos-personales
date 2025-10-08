package com.mtovar.gastospersonales.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtovar.gastospersonales.model.Gasto

@Composable
fun GastoItem(gasto: Gasto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = gasto.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = "Categoria: ${gasto.categoria}")
            Text(text = "Monto: $${gasto.monto}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}