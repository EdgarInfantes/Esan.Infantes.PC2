package com.example.esaninfantespc2.firebase

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseGetDataManager {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun guardarConversion(
        monedaEntrada: String,
        monedaSalida: String,
        montoEntrada: Double,
        montoSalida: Double
    ): Result<Unit> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("Usuario no autenticado"))

        val conversion = hashMapOf(
            "MonedaEntrada" to monedaEntrada,
            "MonedaSalida" to monedaSalida,
            "MontoEntrada" to montoEntrada,
            "MontoSalida" to montoSalida,
            "uid" to uid,
            "timestamp" to Timestamp.now() // Opcional para orden cronológico
        )

        return try {
            firestore.collection("historial")
                .add(conversion)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
