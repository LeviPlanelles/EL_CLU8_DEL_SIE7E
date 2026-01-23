package com.example.el_clu8_del_sie7e.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * =====================================================================================
 * REGISTERVIEWMODEL.KT - VIEWMODEL PARA LA PANTALLA DE REGISTRO
 * =====================================================================================
 *
 * Este ViewModel maneja el estado y la lógica de la pantalla de registro.
 *
 * FUNCIONALIDADES:
 * ----------------
 * 1. Almacena el estado de los campos del formulario (nombre, email, fecha nac., password, etc.)
 * 2. Maneja la visibilidad de las contraseñas
 * 3. Valida los datos (pendiente de implementar validaciones complejas)
 * 4. Simula el proceso de registro
 *
 * =====================================================================================
 */
class RegisterViewModel : ViewModel() {

    // ==================================================================================
    // ESTADO DE LA UI (State)
    // Usamos mutableStateOf para que Compose observe los cambios y redibuje la UI
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

    // ==================================================================================
    // FUNCIONES DE ACTUALIZACIÓN (Events)
    // Estas funciones son llamadas desde la UI para actualizar el estado
    // ==================================================================================

    fun onFullNameChange(newValue: String) {
        fullName = newValue
    }

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onBirthDateChange(newValue: String) {
        birthDate = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword = newValue
    }

    fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    fun toggleConfirmPasswordVisibility() {
        isConfirmPasswordVisible = !isConfirmPasswordVisible
    }

    fun onTermsAcceptedChange(newValue: Boolean) {
        termsAccepted = newValue
    }

    /**
     * Lógica para procesar el registro
     * Retorna true si el registro fue exitoso (simulado)
     */
    fun onRegisterClick(): Boolean {
        // Aquí irían las validaciones y la llamada al backend
        if (fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank() &&
            password == confirmPassword && termsAccepted) {
            return true
        }
        return false
    }
}
