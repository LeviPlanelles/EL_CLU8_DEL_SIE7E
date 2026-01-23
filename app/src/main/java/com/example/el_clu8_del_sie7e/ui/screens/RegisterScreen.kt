package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.*
import com.example.el_clu8_del_sie7e.viewmodel.RegisterViewModel

/**
 * REGISTERSCREEN.KT - PANTALLA DE REGISTRO
 * Optimización de espaciados y alineación fiel al diseño.
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RegisterBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            // 1. Cabecera (Reducida a 64dp de altura para que sea menos prominente)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "EL CLU8 DEL SIE7E",
                    style = MaterialTheme.typography.titleMedium,
                    color = AccentGold,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    fontSize = 20.sp
                )
            }

            HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Título "REGÍSTRATE" con tamaño 22sp
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = AccentGold,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "REGÍSTRATE",
                    color = AccentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }

            Text(
                text = "Únete al club más exclusivo y vive la experiencia",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                fontSize = 11.sp
            )

            // 3. Campos del formulario (19dp de separación entre bloques)
            RegisterInputField(
                label = "Nombre Completo",
                placeholder = "Ej. Guillermo Cholbi",
                value = viewModel.fullName,
                onValueChange = { viewModel.onFullNameChange(it) },
                leadingIcon = Icons.Filled.Person
            )

            RegisterInputField(
                label = "Correo Electrónico",
                placeholder = "usuario@ejemplo.com",
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                leadingIcon = Icons.Filled.Email
            )

            RegisterInputField(
                label = "Fecha de Nacimiento",
                placeholder = "mm/dd/yyyy",
                value = viewModel.birthDate,
                onValueChange = { viewModel.onBirthDateChange(it) },
                leadingIcon = Icons.Filled.CalendarToday,
                trailingIcon = {
                    Icon(Icons.Filled.CalendarToday, null, tint = AccentGold, modifier = Modifier.size(18.dp))
                }
            )

            RegisterInputField(
                label = "Contraseña",
                placeholder = "•••••••••",
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
                isVisible = viewModel.isPasswordVisible,
                onToggleVisibility = { viewModel.togglePasswordVisibility() }
            )

            RegisterInputField(
                label = "Confirmar Contraseña",
                placeholder = "•••••••••",
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
                isVisible = viewModel.isConfirmPasswordVisible,
                onToggleVisibility = { viewModel.toggleConfirmPasswordVisibility() }
            )

            // 4. Checkbox y Términos (Texto reducido a 9sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), // Reducido el padding vertical para ajustar margen
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.termsAccepted,
                    onCheckedChange = { viewModel.onTermsAcceptedChange(it) },
                    modifier = Modifier.size(24.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = AccentGold,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.Black
                    )
                )
                Text(
                    text = buildAnnotatedString {
                        append("He leído y acepto los ")
                        withStyle(style = SpanStyle(color = AccentGold)) {
                            append("términos y condiciones")
                        }
                        append(" y la política de privacidad")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 9.sp,
                    lineHeight = 12.sp
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp), // Reducido de 20dp a 10dp
                color = Color.Gray.copy(alpha = 0.1f)
            )

            // 5. Botón CREAR CUENTA
            Button(
                onClick = { if (viewModel.onRegisterClick()) navController.navigate(Routes.LOGIN_SCREEN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.verticalGradient(colors = listOf(ButtonRedStart, ButtonRedCenter, ButtonRedEnd))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "CREAR CUENTA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                }
            }

            // 6. Footer
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "¿Ya eres miembro? ", color = TextSecondary, fontSize = 13.sp)
                Text(
                    text = "Inicia sesión",
                    color = AccentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { navController.navigate(Routes.LOGIN_SCREEN) }
                )
            }
        }
    }
}

@Composable
fun RegisterInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    isVisible: Boolean = false,
    onToggleVisibility: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.padding(bottom = 19.dp)) {
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            placeholder = { Text(placeholder, color = TextPlaceholder, fontSize = 14.sp) },
            leadingIcon = {
                Icon(imageVector = leadingIcon, contentDescription = null, tint = AccentGold.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
            },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPassword && !isVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { onToggleVisibility?.invoke() }) {
                        Icon(imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = null, tint = AccentGold, modifier = Modifier.size(20.dp))
                    }
                }
            } else trailingIcon,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceDark,
                unfocusedContainerColor = SurfaceDark,
                focusedBorderColor = AccentGold,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                cursorColor = AccentGold,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        RegisterScreen(navController = rememberNavController())
    }
}
