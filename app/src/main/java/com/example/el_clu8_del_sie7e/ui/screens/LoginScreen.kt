package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppLogo
import com.example.el_clu8_del_sie7e.ui.components.PrimaryButton
import com.example.el_clu8_del_sie7e.ui.components.SecondaryButton
import com.example.el_clu8_del_sie7e.ui.components.StyledTextField
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge

/**
 * =====================================================================================
 * LOGINSCREEN.KT - PANTALLA DE INICIO DE SESION
 * =====================================================================================
 *
 * Esta pantalla permite al usuario iniciar sesion con su usuario y contrasena.
 *
 * CONCEPTOS DE COMPOSE QUE SE USAN AQUI:
 * --------------------------------------
 *
 * 1. remember + mutableStateOf:
 *    - Crea una variable de estado que sobrevive a las recomposiciones
 *    - Cuando el valor cambia, Compose redibuja automaticamente los elementos afectados
 *
 * 2. by (delegado):
 *    - Simplifica el acceso al valor del estado
 *    - Sin "by": username.value = "algo"
 *    - Con "by": username = "algo"
 *
 * 3. State Hoisting (Elevacion de Estado):
 *    - Los componentes (TextField, Button) NO manejan su propio estado
 *    - El estado se "eleva" al composable padre (LoginScreen)
 *    - Esto hace los componentes mas reutilizables y faciles de testear
 *
 * 4. VisualTransformation:
 *    - Transforma como se muestra el texto sin cambiar su valor
 *    - PasswordVisualTransformation() muestra puntos en lugar de letras
 *
 * ESTRUCTURA DE LA PANTALLA:
 * --------------------------
 * - Logo de la app (AppLogo)
 * - Campo de usuario (StyledTextField)
 * - Campo de contrasena (StyledTextField con icono de ojo)
 * - Link "Olvide mi contrasena"
 * - Boton "INICIAR SESION" (PrimaryButton)
 * - Separador
 * - Boton "REGISTRARSE" (SecondaryButton)
 * - Icono de huella dactilar
 *
 * =====================================================================================
 */

/**
 * Pantalla de Login.
 *
 * @param navController Controlador de navegacion para ir a otras pantallas
 *
 * NOTA: Actualmente el estado se maneja localmente en la pantalla.
 * En una app real, deberiamos usar un LoginViewModel para:
 * - Validar las credenciales
 * - Llamar a la API de autenticacion
 * - Manejar estados de carga y errores
 */
@Composable
fun LoginScreen(navController: NavController) {

    // ====================================================================================
    // ESTADO DE LA PANTALLA
    // ====================================================================================
    /**
     * Variables de estado para los campos del formulario.
     *
     * remember: Guarda el valor entre recomposiciones
     * mutableStateOf: Crea un estado observable (cuando cambia, Compose redibuja)
     * by: Delegado que permite acceder directamente al valor (sin .value)
     */

    // Estado del campo de usuario
    var username by remember { mutableStateOf("") }

    // Estado del campo de contrasena
    var password by remember { mutableStateOf("") }

    // Estado para mostrar/ocultar la contrasena
    var passwordVisible by remember { mutableStateOf(false) }

    // ====================================================================================
    // INTERFAZ DE USUARIO (UI)
    // ====================================================================================
    /**
     * Box como contenedor principal con el fondo gradiente.
     *
     * IMPORTANTE: Usamos el MISMO gradiente que en SplashScreen
     * para mantener la consistencia visual de la app.
     */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GradientCenter,  // Rojo claro en el centro
                        GradientEdge     // Rojo oscuro en los bordes
                    ),
                    radius = 900f
                )
            )
    ) {
        // Columna principal con scroll para pantallas pequenas
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())  // Permite scroll si no cabe
                .padding(horizontal = 24.dp)            // Padding lateral
                .padding(vertical = 48.dp),             // Padding arriba y abajo
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ------------------------------------------------------------------
            // LOGO DE LA APP
            // ------------------------------------------------------------------
            /**
             * Componente reutilizable que muestra:
             * - Icono de corona
             * - "EL CLU8 DEL SIE7E"
             * - "EXCLUSIVIDAD . LUJO . JUEGO"
             */
            AppLogo()

            Spacer(modifier = Modifier.height(48.dp))

            // ------------------------------------------------------------------
            // CAMPO DE USUARIO
            // ------------------------------------------------------------------
            /**
             * StyledTextField es nuestro componente reutilizable de campo de texto.
             *
             * value: El valor actual del campo
             * onValueChange: Funcion que se llama cuando el usuario escribe
             * leadingIcon: Icono al inicio del campo
             */
            StyledTextField(
                value = username,
                onValueChange = { nuevoValor ->
                    username = nuevoValor  // Actualizamos el estado
                },
                label = "Usuario",
                leadingIcon = Icons.Filled.Person  // Icono de persona
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // CAMPO DE CONTRASENA
            // ------------------------------------------------------------------
            /**
             * Campo de contrasena con icono para mostrar/ocultar.
             *
             * trailingIcon: Icono al final del campo (el ojo)
             * visualTransformation: Transforma el texto visualmente
             */
            StyledTextField(
                value = password,
                onValueChange = { nuevoValor ->
                    password = nuevoValor
                },
                label = "Contrasena",
                leadingIcon = Icons.Filled.Lock,  // Icono de candado
                trailingIcon = {
                    // Icono que cambia segun si la contrasena es visible o no
                    val iconoOjo = if (passwordVisible) {
                        Icons.Filled.Visibility      // Ojo abierto
                    } else {
                        Icons.Filled.VisibilityOff   // Ojo cerrado
                    }

                    // Boton que al presionarlo cambia la visibilidad
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible  // Toggle
                        }
                    ) {
                        Icon(
                            imageVector = iconoOjo,
                            contentDescription = if (passwordVisible) {
                                "Ocultar contrasena"
                            } else {
                                "Mostrar contrasena"
                            }
                        )
                    }
                },
                // Si passwordVisible es true, muestra el texto normal
                // Si es false, muestra puntos (oculta la contrasena)
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            // ------------------------------------------------------------------
            // LINK "OLVIDE MI CONTRASENA"
            // ------------------------------------------------------------------
            /**
             * TextButton es un boton sin fondo, solo texto.
             * Se usa para acciones secundarias o links.
             */
            TextButton(
                onClick = {
                    // TODO: Navegar a pantalla de recuperar contrasena
                    // navController.navigate(Routes.FORGOT_PASSWORD_SCREEN)
                }
            ) {
                Text(
                    text = "Olvide mi contrasena",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // BOTON DE INICIAR SESION
            // ------------------------------------------------------------------
            /**
             * PrimaryButton es nuestro boton principal reutilizable.
             * Tiene fondo dorado y texto oscuro.
             */
            PrimaryButton(
                text = "INICIAR SESION",
                onClick = {
                    // TODO: Implementar logica de login
                    // 1. Validar que username y password no esten vacios
                    // 2. Llamar al ViewModel para autenticar
                    // 3. Si es exitoso, navegar al Lobby
                    navController.navigate(Routes.LOBBY_SCREEN)
                },
                icon = Icons.Filled.Login  // Icono de login
            )

            // ------------------------------------------------------------------
            // SEPARADOR
            // ------------------------------------------------------------------
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 24.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )

            // ------------------------------------------------------------------
            // BOTON DE REGISTRARSE
            // ------------------------------------------------------------------
            /**
             * SecondaryButton es nuestro boton secundario.
             * Tiene fondo transparente y borde/texto dorado.
             */
            SecondaryButton(
                text = "REGISTRARSE",
                onClick = {
                    navController.navigate(Routes.REGISTER_SCREEN)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ------------------------------------------------------------------
            // ICONO DE HUELLA DACTILAR
            // ------------------------------------------------------------------
            /**
             * Icono decorativo para indicar que en el futuro
             * se podra usar autenticacion biometrica.
             *
             * TODO: Implementar login con huella dactilar usando BiometricPrompt
             */
            Icon(
                imageVector = Icons.Filled.Fingerprint,
                contentDescription = "Iniciar sesion con huella dactilar",
                tint = AccentGold,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
/**
 * Preview para ver la pantalla en Android Studio.
 *
 * showSystemUi = true muestra la barra de estado y navegacion
 * para ver como se vera en un dispositivo real.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        LoginScreen(navController = rememberNavController())
    }
}
