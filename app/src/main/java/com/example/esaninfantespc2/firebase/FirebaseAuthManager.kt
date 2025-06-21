package com.example.esaninfantespc2.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.util.Date

object FirebaseAuthManager {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Inicia sesión con email y contraseña.
     * Valida que no estén vacíos y opcionalmente verifica si el email está verificado.
     */
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("El correo y la contraseña no deben estar vacíos."))
        }

        return try {
            auth.signInWithEmailAndPassword(email, password).await()

            val user = auth.currentUser

            Log.d("FirebaseAuthManager", "Login exitoso con $email")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error al iniciar sesión", e)
            Result.failure(e)
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logoutUser() {
        auth.signOut()
        Log.d("FirebaseAuthManager", "Sesión cerrada.")
    }

    /**
     * Envía un correo de recuperación de contraseña.
     */
    suspend fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Log.d("FirebaseAuthManager", "Correo de recuperación enviado a $email")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirebaseAuthManager", "Error al enviar recuperación", e)
            Result.failure(e)
        }
    }

    /**
     * Devuelve el UID del usuario logueado actualmente, o null si no hay sesión.
     */
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    /**
     * Verifica si hay un usuario actualmente autenticado.
     */
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
