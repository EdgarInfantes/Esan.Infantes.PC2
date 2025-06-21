package com.example.esaninfantespc2.util

import com.google.firebase.Timestamp

data class Conversion(
    val monedaEntrada: String,
    val monedaSalida: String,
    val montoEntrada: Double,
    val montoSalida: Double,
    val timestamp: Timestamp
)
