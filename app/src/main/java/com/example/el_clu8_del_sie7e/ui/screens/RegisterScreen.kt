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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.navigation.Routes
import com.example.el_clu8_del_sie7e.ui.theme.*
import com.example.el_clu8_del_sie7e.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * REGISTERSCREEN.KT - PANTALLA DE REGISTRO CON FIREBASE
 * 
 * Esta pantalla permite al usuario crear una cuenta con Firebase Auth.
 * 
 * FUNCIONALIDADES:
 * - Registro con email y contraseña
 * - Validación de campos
 * - Indicador de carga
 * - Manejo de errores
 * - Envío de email de verificación
 */
@Composable
fun RegisterScreen(
    navController: NavController,
    onRegisterSuccess: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    
    // Observar estado del ViewModel
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val successMessage = viewModel.successMessage
    val registerSuccess = viewModel.registerSuccess
    
    // Efecto para manejar navegación cuando el registro es exitoso
    LaunchedEffect(registerSuccess) {
        if (registerSuccess) {
            viewModel.resetRegisterSuccess()
            onRegisterSuccess()
        }
    }
    
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
            // 1. Cabecera
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

            // 2. Título "REGÍSTRATE"
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
                    fontSize = 22.sp,
                    fontFamily = Poppins
                )
            }

            Text(
                text = "Únete al club más exclusivo y vive la experiencia",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                fontSize = 11.sp
            )

            // 3. Campos del formulario
            RegisterInputField(
                label = "Nombre Completo",
                placeholder = "Ej. Guillermo Cholbi",
                value = viewModel.fullName,
                onValueChange = { viewModel.onFullNameChange(it) },
                leadingIcon = Icons.Filled.Person,
                enabled = !isLoading
            )

            RegisterInputField(
                label = "Correo Electrónico",
                placeholder = "usuario@ejemplo.com",
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                leadingIcon = Icons.Filled.Email,
                enabled = !isLoading
            )

            // Campo de Fecha de Nacimiento con DatePicker
            DatePickerField(
                label = "Fecha de Nacimiento",
                placeholder = "dd/mm/yyyy",
                value = viewModel.birthDate,
                onDateSelected = { viewModel.onBirthDateChange(it) },
                enabled = !isLoading
            )

            RegisterInputField(
                label = "Contraseña",
                placeholder = "•••••••••",
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
                isVisible = viewModel.isPasswordVisible,
                onToggleVisibility = { viewModel.togglePasswordVisibility() },
                enabled = !isLoading
            )

            RegisterInputField(
                label = "Confirmar Contraseña",
                placeholder = "•••••••••",
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                leadingIcon = Icons.Filled.Lock,
                isPassword = true,
                isVisible = viewModel.isConfirmPasswordVisible,
                onToggleVisibility = { viewModel.toggleConfirmPasswordVisibility() },
                enabled = !isLoading
            )

            // 4. Checkbox y Términos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.termsAccepted,
                    onCheckedChange = { viewModel.onTermsAcceptedChange(it) },
                    modifier = Modifier.size(24.dp),
                    enabled = !isLoading,
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
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
                color = Color.Gray.copy(alpha = 0.1f)
            )

            // 5. Botón CREAR CUENTA o indicador de carga
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AccentGold)
                }
            } else {
                Button(
                    onClick = { viewModel.onRegisterClick() },
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
                        Text(text = "CREAR CUENTA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp, fontFamily = Poppins)
                    }
                }
            }

            // 6. Mensajes de error o éxito
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage,
                    color = Color(0xFFFF5252),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Poppins
                )
            }
            
            if (successMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = successMessage,
                    color = Color(0xFF00C853),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = Poppins
                )
            }

            // 7. Footer
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "¿Ya eres miembro? ", color = TextSecondary, fontSize = 13.sp, fontFamily = Poppins)
                Text(
                    text = "Inicia sesión",
                    color = AccentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    fontFamily = Poppins,
                    modifier = Modifier.clickable { navController.navigate(Routes.LOGIN_SCREEN) }
                )
            }
        }
    }
}

/**
 * DATEPICKERFIELD - Campo de fecha con DatePicker de Material 3
 * 
 * Muestra un campo de texto con un icono de calendario a la derecha.
 * Al hacer clic, abre un DatePickerDialog para seleccionar la fecha.
 * 
 * @param label Etiqueta del campo
 * @param placeholder Texto de placeholder
 * @param value Valor actual de la fecha
 * @param onDateSelected Callback cuando se selecciona una fecha
 * @param enabled Si el campo está habilitado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    placeholder: String,
    value: String,
    onDateSelected: (String) -> Unit,
    enabled: Boolean = true
) {
    // Estado para mostrar/ocultar el DatePicker
    var showDatePicker by remember { mutableStateOf(false) }
    
    // Estado del DatePicker
    val datePickerState = rememberDatePickerState()
    
    // Formateador de fecha
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    
    Column(modifier = Modifier.padding(bottom = 19.dp)) {
        // Label del campo
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Poppins
        )
        
        // Campo de texto clickable (solo lectura)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(SurfaceDark, RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = if (showDatePicker) AccentGold else Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(enabled = enabled) { showDatePicker = true },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Texto de la fecha o placeholder
                Text(
                    text = if (value.isNotEmpty()) value else placeholder,
                    color = if (value.isNotEmpty()) Color.White else TextPlaceholder,
                    fontSize = 15.sp,
                    fontFamily = Poppins
                )
                
                // Icono de calendario a la derecha
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Seleccionar fecha",
                    tint = AccentGold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    
    // DatePickerDialog de Material 3
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Obtener la fecha seleccionada y formatearla
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = millis
                            val formattedDate = dateFormatter.format(calendar.time)
                            onDateSelected(formattedDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar", color = AccentGold, fontFamily = Poppins)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Color.Gray, fontFamily = Poppins)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = SurfaceDark
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = SurfaceDark,
                    titleContentColor = AccentGold,
                    headlineContentColor = Color.White,
                    weekdayContentColor = AccentGold,
                    subheadContentColor = Color.White,
                    yearContentColor = Color.White,
                    currentYearContentColor = AccentGold,
                    selectedYearContentColor = Color.Black,
                    selectedYearContainerColor = AccentGold,
                    dayContentColor = Color.White,
                    selectedDayContentColor = Color.Black,
                    selectedDayContainerColor = AccentGold,
                    todayContentColor = AccentGold,
                    todayDateBorderColor = AccentGold,
                    dayInSelectionRangeContentColor = Color.White,
                    navigationContentColor = AccentGold
                )
            )
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
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    Column(modifier = Modifier.padding(bottom = 19.dp)) {
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.padding(bottom = 6.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Poppins
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
                    IconButton(onClick = { onToggleVisibility?.invoke() }, enabled = enabled) {
                        Icon(imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, contentDescription = null, tint = AccentGold, modifier = Modifier.size(20.dp))
                    }
                }
            } else trailingIcon,
            singleLine = true,
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceDark,
                unfocusedContainerColor = SurfaceDark,
                focusedBorderColor = AccentGold,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                cursorColor = AccentGold,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledContainerColor = SurfaceDark.copy(alpha = 0.5f),
                disabledTextColor = Color.White.copy(alpha = 0.5f)
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
