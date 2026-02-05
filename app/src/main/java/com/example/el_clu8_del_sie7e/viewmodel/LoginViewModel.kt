package com.example.el_clu8_del_sie7e.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.el_clu8_del_sie7e.data.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * =====================================================================================
 * LOGINVIEWMODEL.KT - VIEWMODEL DE LA PANTALLA DE LOGIN CON FIREBASE
 * =====================================================================================
 *
 * Este ViewModel maneja la logica y el estado de la pantalla de Login usando Firebase Auth.
 *
 * FUNCIONALIDADES:
 * - Login con email y contraseña
 * - Validación de campos
 * - Manejo de errores de Firebase
 * - Estado de carga
 * - Navegación a recuperar contraseña
 *
 * =====================================================================================
 */
class LoginViewModel : ViewModel() {

    // Repositorio de autenticación
    private val authRepository = AuthRepository.getInstance()

    // ==================================================================================
    // ESTADO DE LA PANTALLA
    // ==================================================================================
    
    /**
     * Estado del campo de email (antes era username).
     * Firebase usa email para autenticación.
     */
    var email by mutableStateOf("")
        private set

    /**
     * Estado del campo de contraseña.
     */
    var password by mutableStateOf("")
        private set

    /**
     * Estado de visibilidad de la contraseña.
     * true = se ve el texto, false = se ven puntos
     */
    var isPasswordVisible by mutableStateOf(false)
        private set

    /**
     * Estado de carga.
     * true = estamos procesando el login
     */
    var isLoading by mutableStateOf(false)
        private set

    /**
     * Mensaje de error para mostrar al usuario.
     * null = no hay error, String = hay un error que mostrar
     */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    /**
     * Indica si el login fue exitoso.
     * La pantalla debe navegar al lobby cuando sea true.
     */
    var loginSuccess by mutableStateOf(false)
        private set

    // ==================================================================================
    // FUNCIONES DE EVENTOS
    // ==================================================================================

    /**
     * Se llama cuando el usuario escribe en el campo de email.
     */
    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    /**
     * Se llama cuando el usuario escribe en el campo de contraseña.
     */
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    /**
     * Se llama cuando el usuario presiona el icono de mostrar/ocultar contraseña.
     */
    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    /**
     * Se llama cuando el usuario presiona el botón de iniciar sesión.
     * Realiza el login con Firebase Auth.
     */
    fun onLoginClick() {
        // Validación básica
        if (email.isBlank()) {
            errorMessage = "Por favor ingresa tu email"
            return
        }

        if (password.isBlank()) {
            errorMessage = "Por favor ingresa tu contraseña"
            return
        }

        if (password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        // Realizar login con Firebase
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            
            val success = authRepository.login(email, password)
            
            isLoading = false
            
            if (success) {
                loginSuccess = true
            } else {
                errorMessage = authRepository.errorMessage.value
            }
        }
    }

    /**
     * Se llama cuando el usuario presiona "Olvidé mi contraseña".
     * Navega a la pantalla de recuperar contraseña.
     */
    fun onForgotPasswordClick() {
        // La navegación se maneja en la pantalla
    }

    /**
     * Se llama cuando el usuario presiona el botón de registrarse.
     * Navega a la pantalla de registro.
     */
    fun onRegisterClick() {
        // La navegación se maneja en la pantalla
    }

    /**
     * Limpia el mensaje de error.
     */
    fun clearError() {
        errorMessage = null
        authRepository.clearError()
    }

    /**
     * Resetea el estado de éxito después de navegar.
     */
    fun resetLoginSuccess() {
        loginSuccess = false
    }
}