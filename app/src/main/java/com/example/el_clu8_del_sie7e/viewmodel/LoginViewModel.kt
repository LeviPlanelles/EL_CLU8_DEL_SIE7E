package com.example.el_clu8_del_sie7e.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * =====================================================================================
 * LOGINVIEWMODEL.KT - VIEWMODEL DE LA PANTALLA DE LOGIN
 * =====================================================================================
 *
 * Este ViewModel maneja la logica y el estado de la pantalla de Login.
 *
 * QUE ES UN VIEWMODEL?
 * --------------------
 * Un ViewModel es una clase que:
 * 1. Guarda y gestiona el estado de la UI
 * 2. Sobrevive a cambios de configuracion (rotacion de pantalla, etc.)
 * 3. Separa la logica de negocio de la UI
 *
 * POR QUE USAR VIEWMODEL?
 * -----------------------
 * Imagina que el usuario esta escribiendo su contrasena y rota el telefono.
 * Sin ViewModel, la pantalla se reconstruye y pierde todo el texto.
 * Con ViewModel, el estado se mantiene y el usuario no pierde nada.
 *
 * ARQUITECTURA MVVM (Model-View-ViewModel):
 * -----------------------------------------
 *
 * ┌─────────────────┐      ┌─────────────────┐      ┌─────────────────┐
 * │      MODEL      │ <--> │   VIEWMODEL     │ <--> │      VIEW       │
 * │  (Datos, API,   │      │  (Estado,       │      │  (Composables,  │
 * │   Repository)   │      │   Logica)       │      │   UI)           │
 * └─────────────────┘      └─────────────────┘      └─────────────────┘
 *
 * - VIEW (LoginScreen): Solo muestra la UI y captura eventos del usuario
 * - VIEWMODEL (LoginViewModel): Procesa eventos y expone el estado
 * - MODEL (Repository, API): Accede a datos (futuro)
 *
 * ESTADO EN COMPOSE:
 * ------------------
 * Usamos mutableStateOf para crear estado observable.
 * Cuando el estado cambia, Compose redibuja automaticamente los composables afectados.
 *
 * =====================================================================================
 */

/**
 * ViewModel para la pantalla de Login.
 *
 * COMO USAR ESTE VIEWMODEL EN LA PANTALLA:
 * ----------------------------------------
 * ```kotlin
 * @Composable
 * fun LoginScreen(
 *     viewModel: LoginViewModel = viewModel()  // Compose crea o recupera el ViewModel
 * ) {
 *     // Acceder al estado
 *     val username = viewModel.username
 *     val isLoading = viewModel.isLoading
 *
 *     // Llamar a funciones del ViewModel
 *     StyledTextField(
 *         value = username,
 *         onValueChange = { viewModel.onUsernameChange(it) }
 *     )
 *
 *     PrimaryButton(
 *         text = "INICIAR SESION",
 *         onClick = { viewModel.onLoginClick() }
 *     )
 * }
 * ```
 */
class LoginViewModel : ViewModel() {

    // ==================================================================================
    // ESTADO DE LA PANTALLA
    // ==================================================================================
    /**
     * Estado del campo de usuario.
     *
     * "by" es un delegado que permite acceder directamente al valor:
     * - Sin "by": _username.value = "algo"
     * - Con "by": username = "algo"
     *
     * "private set" significa que solo el ViewModel puede modificar el valor,
     * pero cualquiera puede leerlo.
     */
    var username by mutableStateOf("")
        private set

    /**
     * Estado del campo de contrasena.
     */
    var password by mutableStateOf("")
        private set

    /**
     * Estado de visibilidad de la contrasena.
     * true = se ve el texto, false = se ven puntos
     */
    var isPasswordVisible by mutableStateOf(false)
        private set

    /**
     * Estado de carga.
     * true = estamos procesando el login, false = no estamos cargando
     */
    var isLoading by mutableStateOf(false)
        private set

    /**
     * Mensaje de error para mostrar al usuario.
     * null = no hay error, String = hay un error que mostrar
     */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // ==================================================================================
    // FUNCIONES DE EVENTOS (Las llama la UI cuando algo pasa)
    // ==================================================================================

    /**
     * Se llama cuando el usuario escribe en el campo de usuario.
     *
     * @param newUsername El nuevo valor del campo
     *
     * EJEMPLO DE USO:
     * ```kotlin
     * StyledTextField(
     *     value = viewModel.username,
     *     onValueChange = { viewModel.onUsernameChange(it) }
     * )
     * ```
     */
    fun onUsernameChange(newUsername: String) {
        username = newUsername
        // Limpiamos el error cuando el usuario empieza a corregir
        errorMessage = null
    }

    /**
     * Se llama cuando el usuario escribe en el campo de contrasena.
     *
     * @param newPassword El nuevo valor del campo
     */
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    /**
     * Se llama cuando el usuario presiona el icono de mostrar/ocultar contrasena.
     */
    fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
    }

    /**
     * Se llama cuando el usuario presiona el boton de iniciar sesion.
     *
     * Aqui va la logica de autenticacion:
     * 1. Validar que los campos no esten vacios
     * 2. Mostrar indicador de carga
     * 3. Llamar a la API de autenticacion
     * 4. Manejar el resultado (exito o error)
     *
     * TODO: Implementar la logica real de autenticacion
     */
    fun onLoginClick() {
        // Validacion basica
        if (username.isBlank()) {
            errorMessage = "Por favor ingresa tu usuario"
            return
        }

        if (password.isBlank()) {
            errorMessage = "Por favor ingresa tu contrasena"
            return
        }

        // TODO: Implementar llamada a API de autenticacion
        // Ejemplo de como seria:
        //
        // viewModelScope.launch {
        //     isLoading = true
        //     try {
        //         val result = authRepository.login(username, password)
        //         if (result.isSuccess) {
        //             // Navegar al lobby
        //         } else {
        //             errorMessage = "Usuario o contrasena incorrectos"
        //         }
        //     } catch (e: Exception) {
        //         errorMessage = "Error de conexion. Intenta de nuevo."
        //     } finally {
        //         isLoading = false
        //     }
        // }
    }

    /**
     * Se llama cuando el usuario presiona "Olvide mi contrasena".
     *
     * TODO: Implementar navegacion a pantalla de recuperar contrasena
     */
    fun onForgotPasswordClick() {
        // TODO: Navegar a pantalla de recuperar contrasena
    }

    /**
     * Se llama cuando el usuario presiona el boton de registrarse.
     *
     * TODO: Implementar navegacion a pantalla de registro
     */
    fun onRegisterClick() {
        // TODO: Navegar a pantalla de registro
    }

    /**
     * Se llama cuando el usuario presiona el icono de huella dactilar.
     *
     * TODO: Implementar autenticacion biometrica
     */
    fun onFingerprintClick() {
        // TODO: Implementar login con BiometricPrompt
    }

    /**
     * Limpia el mensaje de error.
     * Util si queremos cerrar un Snackbar de error manualmente.
     */
    fun clearError() {
        errorMessage = null
    }
}
