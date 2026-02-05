package com.example.el_clu8_del_sie7e.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

/**
 * =====================================================================================
 * AUTHREPOSITORY.KT - REPOSITORIO DE AUTENTICACIÓN CON FIREBASE
 * =====================================================================================
 *
 * Este repositorio maneja todas las operaciones de autenticación usando Firebase Auth.
 * Incluye: login, registro, logout, recuperación de contraseña y estado de sesión.
 *
 * CARACTERÍSTICAS:
 * - Persistencia automática de sesión
 * - Escucha de cambios en el estado de autenticación
 * - Manejo de errores específicos de Firebase
 *
 * =====================================================================================
 */
class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Estado actual del usuario
    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Mensaje de error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        // Escuchar cambios en el estado de autenticación
        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
        }
    }

    /**
     * Iniciar sesión con email y contraseña
     *
     * @param email Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @return true si el login fue exitoso, false si hubo error
     */
    suspend fun login(email: String, password: String): Boolean {
        return try {
            _isLoading.value = true
            _errorMessage.value = null
            
            auth.signInWithEmailAndPassword(email, password).await()
            _isLoading.value = false
            true
        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = when {
                e.message?.contains("There is no user record") == true ->
                    "Usuario no encontrado. Verifica tu email."
                e.message?.contains("The password is invalid") == true ->
                    "Contraseña incorrecta. Intenta nuevamente."
                e.message?.contains("The email address is badly formatted") == true ->
                    "Email inválido. Usa un formato correcto."
                else -> "Error al iniciar sesión: ${e.message}"
            }
            false
        }
    }

    /**
     * Registrar nuevo usuario con email y contraseña
     *
     * @param email Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @param displayName Nombre para mostrar del usuario
     * @return true si el registro fue exitoso, false si hubo error
     */
    suspend fun register(email: String, password: String, displayName: String = ""): Boolean {
        return try {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            
            // Actualizar el perfil con el nombre
            if (displayName.isNotEmpty()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                result.user?.updateProfile(profileUpdates)?.await()
            }
            
            // Enviar email de verificación
            result.user?.sendEmailVerification()?.await()
            
            _isLoading.value = false
            true
        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = when {
                e.message?.contains("The email address is already in use") == true ->
                    "Este email ya está registrado. Usa otro o inicia sesión."
                e.message?.contains("The email address is badly formatted") == true ->
                    "Email inválido. Usa un formato correcto."
                e.message?.contains("The given password is invalid") == true ->
                    "Contraseña débil. Usa al menos 6 caracteres."
                else -> "Error al registrar: ${e.message}"
            }
            false
        }
    }

    /**
     * Cerrar sesión del usuario actual
     */
    fun logout() {
        auth.signOut()
        _errorMessage.value = null
    }

    /**
     * Enviar email para recuperar contraseña
     *
     * @param email Correo electrónico del usuario
     * @return true si se envió el email, false si hubo error
     */
    suspend fun resetPassword(email: String): Boolean {
        return try {
            _isLoading.value = true
            _errorMessage.value = null
            
            auth.sendPasswordResetEmail(email).await()
            
            _isLoading.value = false
            true
        } catch (e: Exception) {
            _isLoading.value = false
            _errorMessage.value = when {
                e.message?.contains("There is no user record") == true ->
                    "No existe usuario con este email."
                e.message?.contains("The email address is badly formatted") == true ->
                    "Email inválido. Usa un formato correcto."
                else -> "Error al enviar email: ${e.message}"
            }
            false
        }
    }

    /**
     * Verificar si el email del usuario está verificado
     */
    fun isEmailVerified(): Boolean {
        return auth.currentUser?.isEmailVerified ?: false
    }

    /**
     * Limpiar mensaje de error
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Obtener el nombre de usuario actual
     */
    fun getCurrentUserName(): String {
        return auth.currentUser?.displayName ?: "Usuario"
    }

    /**
     * Obtener el email del usuario actual
     */
    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(): AuthRepository {
            return instance ?: synchronized(this) {
                instance ?: AuthRepository().also { instance = it }
            }
        }
    }
}