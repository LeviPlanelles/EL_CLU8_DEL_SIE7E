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
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.el_clu8_del_sie7e.viewmodel.LoginViewModel

/**
 * =====================================================================================
 * LOGINSCREEN.KT - PANTALLA DE INICIO DE SESION CON FIREBASE
 * =====================================================================================
 *
 * Esta pantalla permite al usuario iniciar sesion con email y contraseña usando Firebase Auth.
 *
 * FUNCIONALIDADES:
 * - Login con email y contraseña
 * - Validación de campos
 * - Indicador de carga
 * - Manejo de errores
 * - Navegación a registro
 * - Navegación a recuperar contraseña
 *
 * =====================================================================================
 */

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {

    // Observar estado del ViewModel
    val email = viewModel.email
    val password = viewModel.password
    val isPasswordVisible = viewModel.isPasswordVisible
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val loginSuccess = viewModel.loginSuccess

    // Efecto para manejar navegación cuando el login es exitoso
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            viewModel.resetLoginSuccess()
            onLoginSuccess()
        }
    }

    // ====================================================================================
    // INTERFAZ DE USUARIO (UI)
    // ====================================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GradientCenter,
                        GradientEdge
                    ),
                    radius = 900f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ------------------------------------------------------------------
            // LOGO DE LA APP
            // ------------------------------------------------------------------
            AppLogo()

            Spacer(modifier = Modifier.height(48.dp))

            // ------------------------------------------------------------------
            // CAMPO DE EMAIL
            // ------------------------------------------------------------------
            StyledTextField(
                value = email,
                onValueChange = viewModel::onEmailChange,
                label = "Email",
                leadingIcon = Icons.Filled.Person,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // CAMPO DE CONTRASEÑA
            // ------------------------------------------------------------------
            StyledTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                label = "Contraseña",
                leadingIcon = Icons.Filled.Lock,
                enabled = !isLoading,
                trailingIcon = {
                    val iconoOjo = if (isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(
                        onClick = viewModel::onTogglePasswordVisibility,
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = iconoOjo,
                            contentDescription = if (isPasswordVisible) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            }
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )

            // ------------------------------------------------------------------
            // LINK "OLVIDE MI CONTRASEÑA"
            // ------------------------------------------------------------------
            TextButton(
                onClick = viewModel::onForgotPasswordClick,
                enabled = !isLoading
            ) {
                Text(
                    text = "Olvidé mi contraseña",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // INDICADOR DE CARGA O BOTON DE LOGIN
            // ------------------------------------------------------------------
            if (isLoading) {
                CircularProgressIndicator(
                    color = AccentGold,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                PrimaryButton(
                    text = "INICIAR SESIÓN",
                    onClick = viewModel::onLoginClick,
                    icon = Icons.AutoMirrored.Filled.Login
                )
            }

            // ------------------------------------------------------------------
            // MENSAJE DE ERROR
            // ------------------------------------------------------------------
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Snackbar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    action = {
                        TextButton(onClick = viewModel::clearError) {
                            Text("OK", color = AccentGold)
                        }
                    }
                ) {
                    Text(errorMessage)
                }
            }

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
            SecondaryButton(
                text = "REGISTRARSE",
                onClick = {
                    navController.navigate(Routes.REGISTER_SCREEN)
                },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ------------------------------------------------------------------
            // ICONO DE HUELLA DACTILAR (FUTURO)
            // ------------------------------------------------------------------
            Icon(
                imageVector = Icons.Filled.Fingerprint,
                contentDescription = "Iniciar sesión con huella dactilar",
                tint = AccentGold.copy(alpha = 0.5f),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

// ======================================================================================
// PREVIEW
// ======================================================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        LoginScreen(
            navController = rememberNavController(),
            onLoginSuccess = {}
        )
    }
}