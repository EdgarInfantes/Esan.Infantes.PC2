package com.example.esaninfantespc2.firebase

import com.example.esaninfantespc2.util.Conversion
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
            "timestamp" to Timestamp.now()
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

    suspend fun obtenerHistorialDelUsuario(): Result<List<Conversion>> {
        val uid = auth.currentUser?.uid
            ?: return Result.failure(Exception("Usuario no autenticado"))

        return try {
            val snapshot = firestore
                .collection("historial")
                .whereEqualTo("uid", uid)
                .orderBy("timestamp")
                .get()
                .await()

            val lista = snapshot.documents.mapNotNull { doc ->
                val entrada = doc.getString("MonedaEntrada") ?: return@mapNotNull null
                val salida = doc.getString("MonedaSalida") ?: return@mapNotNull null
                val montoEntrada = doc.getDouble("MontoEntrada") ?: return@mapNotNull null
                val montoSalida = doc.getDouble("MontoSalida") ?: return@mapNotNull null
                val fecha = doc.getTimestamp("timestamp") ?: Timestamp.now()

                Conversion(
                    monedaEntrada = entrada,
                    monedaSalida = salida,
                    montoEntrada = montoEntrada,
                    montoSalida = montoSalida,
                    timestamp = fecha
                )
            }

            Result.success(lista)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
