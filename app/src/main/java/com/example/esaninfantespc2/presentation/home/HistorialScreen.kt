package com.example.esaninfantespc2.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.esaninfantespc2.firebase.FirebaseGetDataManager
import com.example.esaninfantespc2.util.Conversion
import com.example.esaninfantespc2.viewmodel.UserSessionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistorialScreen(
    navController: NavHostController,
    userSessionViewModel: UserSessionViewModel
) {
    var historial by remember { mutableStateOf<List<Conversion>>(emptyList()) }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val result = withContext(Dispatchers.IO) {
            FirebaseGetDataManager.obtenerHistorialDelUsuario()
        }
        cargando = false
        result
            .onSuccess { historial = it }
            .onFailure { error = it.message }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de conversiones",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cargando) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(historial) { item ->
                    HistorialItem(item)
                }
            }
        }
    }
}

@Composable
fun HistorialItem(item: Conversion) {
    val fecha = remember(item.timestamp) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        sdf.format(item.timestamp.toDate())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("De: ${item.montoEntrada} ${item.monedaEntrada}")
            Text("A: ${item.montoSalida} ${item.monedaSalida}")
            Text("Fecha: $fecha", style = MaterialTheme.typography.labelSmall)
        }
    }
}
