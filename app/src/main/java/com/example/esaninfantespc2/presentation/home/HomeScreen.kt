package com.example.esaninfantespc2.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel
import com.example.esaninfantespc2.firebase.FirebaseGetDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    userSessionViewModel: UserSessionViewModel
) {
    val monedas = listOf("PEN", "EUR", "GBP", "JPY", "CLP", "USD")

    var monto by remember { mutableStateOf("") }
    var monedaOrigen by remember { mutableStateOf(monedas.first()) }
    var monedaDestino by remember { mutableStateOf(monedas.last()) }
    var resultado by remember { mutableStateOf<String?>(null) }

    // Tasas hacia USD
    val tasasAUSD = mapOf(
        "PEN" to 0.27,
        "EUR" to 1.08,
        "GBP" to 1.26,
        "JPY" to 0.0064,
        "CLP" to 0.0011,
        "USD" to 1.0
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Conversor de Monedas", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            modifier = Modifier.fillMaxWidth()
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            MonedaDropdownMenu(
                label = "Moneda de origen",
                selected = monedaOrigen,
                options = monedas.filter { it != monedaDestino },
                onSelected = { monedaOrigen = it }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        val temp = monedaOrigen
                        monedaOrigen = monedaDestino
                        monedaDestino = temp
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Intercambiar monedas"
                    )
                }
            }

            MonedaDropdownMenu(
                label = "Moneda de destino",
                selected = monedaDestino,
                options = monedas.filter { it != monedaOrigen },
                onSelected = { monedaDestino = it }
            )
        }

        Button(
            onClick = {
                val montoDouble = monto.toDoubleOrNull()
                val tasaOrigen = tasasAUSD[monedaOrigen] ?: 0.0
                val tasaDestino = tasasAUSD[monedaDestino] ?: 0.0

                resultado = if (montoDouble != null) {
                    val usd = montoDouble * tasaOrigen
                    val final = usd / tasaDestino

                    CoroutineScope(Dispatchers.IO).launch {
                        FirebaseGetDataManager.guardarConversion(
                            monedaEntrada = monedaOrigen,
                            monedaSalida = monedaDestino,
                            montoEntrada = montoDouble,
                            montoSalida = final
                        )
                    }

                    "$monto $monedaOrigen equivale a %.2f $monedaDestino".format(final)
                } else {
                    "Monto inválido"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convertir")
        }

        resultado?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun MonedaDropdownMenu(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Box {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                enabled = false
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { moneda ->
                    DropdownMenuItem(
                        text = { Text(moneda) },
                        onClick = {
                            onSelected(moneda)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
