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
 * REGISTERVIEWMODEL.KT - VIEWMODEL PARA LA PANTALLA DE REGISTRO CON FIREBASE
 * =====================================================================================
 *
 * Este ViewModel maneja el estado y la lógica de la pantalla de registro usando Firebase Auth.
 *
 * FUNCIONALIDADES:
 * - Registro con email y contraseña
 * - Validación de campos
 * - Manejo de errores de Firebase
 * - Estado de carga
 * - Envío de email de verificación
 *
 * =====================================================================================
 */
class RegisterViewModel : ViewModel() {

    // Repositorio de autenticación
    private val authRepository = AuthRepository.getInstance()

    // ==================================================================================
    // ESTADO DE LA UI
    // ==================================================================================

    /** Nombre completo del usuario */
    var fullName by mutableStateOf("")
        private set

    /** Correo electrónico */
    var email by mutableStateOf("")
        private set

    /** Fecha de nacimiento */
    var birthDate by mutableStateOf("")
        private set

    /** Contraseña */
    var password by mutableStateOf("")
        private set

    /** Confirmación de contraseña */
    var confirmPassword by mutableStateOf("")
        private set

    /** Visibilidad de la contraseña principal */
    var isPasswordVisible by mutableStateOf(false)
        private set

    /** Visibilidad de la confirmación de contraseña */
    var isConfirmPasswordVisible by mutableStateOf(false)
        private set

    /** Estado del checkbox de términos y condiciones */
    var termsAccepted by mutableStateOf(false)
        private set

    /** Estado de carga */
    var isLoading by mutableStateOf(false)
        private set

    /** Mensaje de error */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    /** Indica si el registro fue exitoso */
    var registerSuccess by mutableStateOf(false)
        private set

    /** Mensaje de éxito */
    var successMessage by mutableStateOf<String?>(null)
        private set

    // ==================================================================================
    // FUNCIONES DE ACTUALIZACIÓN
    // ==================================================================================

    fun onFullNameChange(newValue: String) {
        fullName = newValue
        errorMessage = null
    }

    fun onEmailChange(newValue: String) {
        email = newValue
        errorMessage = null
    }

    fun onBirthDateChange(newValue: String) {
        birthDate = newValue
        errorMessage = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        errorMessage = null
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword = newValue
        errorMessage = null
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }

    fun onTermsAcceptedChange(newValue: Boolean) {
        termsAccepted = newValue
        errorMessage = null
    }

    /**
     * Lógica para procesar el registro con Firebase Auth
     */
    fun onRegisterClick() {
        // Validaciones
        if (fullName.isBlank()) {
            errorMessage = "Por favor ingresa tu nombre completo"
            return
        }

        if (email.isBlank()) {
            errorMessage = "Por favor ingresa tu email"
            return
        }

        if (password.isBlank()) {
            errorMessage = "Por favor ingresa una contraseña"
            return
        }

        if (password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        if (password != confirmPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }

        if (!termsAccepted) {
            errorMessage = "Debes aceptar los términos y condiciones"
            return
        }

        // Realizar registro con Firebase
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            
            val success = authRepository.register(email, password, fullName)
            
            isLoading = false
            
            if (success) {
                registerSuccess = true
                successMessage = "¡Registro exitoso! Hemos enviado un email de verificación a $email"
            } else {
                errorMessage = authRepository.errorMessage.value
            }
        }
    }

    /**
     * Limpia el mensaje de error
     */
    fun clearError() {
        errorMessage = null
        authRepository.clearError()
    }

    /**
     * Resetea el estado de éxito
     */
    fun resetRegisterSuccess() {
        registerSuccess = false
        successMessage = null
    }
}