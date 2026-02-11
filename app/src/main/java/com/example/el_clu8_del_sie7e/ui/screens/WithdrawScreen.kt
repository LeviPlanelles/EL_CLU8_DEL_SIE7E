package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.RedButton
import com.example.el_clu8_del_sie7e.ui.components.WithdrawalMethodCard
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed
import com.example.el_clu8_del_sie7e.ui.theme.Tomorrow
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * WITHDRAWSCREEN.KT - PANTALLA DE RETIRADA DE FONDOS
 * =====================================================================================
 *
 * Esta pantalla permite al usuario retirar fondos de su cuenta con:
 * - AppHeader con balance
 * - Título "RETIRADA"
 * - Saldo retirable destacado
 * - Campo de entrada de monto con botón "MÁXIMO"
 * - Límites mínimos y texto "Sin comisiones"
 * - Sección "Método de Retiro" con opciones:
 *   * Transferencia Bancaria (1-3 días, seleccionado por defecto)
 *   * Visa / Mastercard (Instantáneo)
 *   * Criptomonedas (~15 minutos)
 * - Botón "RETIRAR FONDOS"
 * - AppFooter con "Cartera" seleccionado
 *
 * ESTRUCTURA:
 * -----------
 * - Box principal con fondo gradiente
 * - Column con scroll para todo el contenido
 * - AppHeader
 * - Header personalizado con botón back y título
 * - Saldo retirable
 * - Campo de monto con botón máximo
 * - Límites y comisiones
 * - Lista de métodos de retiro seleccionables
 * - Botón de acción principal
 * - AppFooter
 *
 * =====================================================================================
 */

@Composable
fun WithdrawScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel  // Se pasa desde NavGraph (compartido)
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    
    // Estado para el item seleccionado en el footer
    var selectedFooterItem by remember { mutableStateOf("Cartera") }

    // Estado para el monto a retirar
    var withdrawAmount by remember { mutableStateOf("") }

    // Estado para el método seleccionado (0 = Bancaria, 1 = Tarjeta, 2 = Cripto)
    var selectedMethod by remember { mutableStateOf(0) }

    // Estado para mensajes de error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estado para mostrar éxito
    var showSuccess by remember { mutableStateOf(false) }

    // Obtener balance actual del ViewModel
    val currentBalance by balanceViewModel.balance.collectAsState()
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()
    
    // Constantes
    val minWithdrawal = 50.00
    
    // Nombres de métodos para mostrar en mensajes
    val methodNames = listOf("Transferencia Bancaria", "Visa/Mastercard", "Criptomonedas")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ------------------------------------------------------------------
            // APP HEADER
            // ------------------------------------------------------------------
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )

            // ------------------------------------------------------------------
            // CONTENIDO CON SCROLL
            // ------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // ------------------------------------------------------------------
                // HEADER PERSONALIZADO
                // ------------------------------------------------------------------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón de retroceso
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Volver",
                            tint = AccentGold
                        )
                    }

                    // Título "RETIRADA"
                    Text(
                        text = "RETIRADA",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ------------------------------------------------------------------
                // SALDO RETIRABLE
                // ------------------------------------------------------------------
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SALDO RETIRABLE",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Saldo retirable grande con fuente Tomorrow para números
                    Text(
                        text = formattedBalance,
                        style = MaterialTheme.typography.displaySmall,
                        color = AccentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        fontFamily = Tomorrow
                    )
                }

                // Mostrar mensaje de error si existe
                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Mostrar mensaje de éxito
                if (showSuccess) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ Retiro exitoso",
                        color = Color(0xFF00C853),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // MONTO A RETIRAR
                // ------------------------------------------------------------------
                Text(
                    text = "Monto a retirar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de entrada con botón MÁXIMO
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF2A2A2A),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Símbolo de dólar con fuente Tomorrow
                        Text(
                            text = "$",
                            style = MaterialTheme.typography.titleLarge,
                            color = AccentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = Tomorrow
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                        // Campo de texto con fuente Tomorrow para números
                        BasicTextField(
                            value = withdrawAmount,
                            onValueChange = { newValue ->
                                // Filtrar solo números y punto decimal
                                if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                                    withdrawAmount = newValue
                                }
                            },
                            modifier = Modifier.weight(1f),
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = Tomorrow
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            singleLine = true
                        )

                        // Botón MÁXIMO
                        Text(
                            text = "MÁXIMO",
                            style = MaterialTheme.typography.labelMedium,
                            color = AccentGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    color = AccentGold.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .then(
                                    Modifier.clickable {
                                        withdrawAmount = "%.2f".format(currentBalance)
                                        errorMessage = null
                                        showSuccess = false
                                    }
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Límites y comisiones con fuente Tomorrow para números
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            text = "Mínimo: ",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = Poppins
                        )
                        Text(
                            text = "$${"%.2f".format(minWithdrawal)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = Tomorrow
                        )
                    }

                    Text(
                        text = "Sin comisiones",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontFamily = Poppins
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // MÉTODO DE RETIRO
                // ------------------------------------------------------------------
                Text(
                    text = "Método de Retiro",
                    style = MaterialTheme.typography.titleMedium,
                    color = AccentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Transferencia Bancaria
                WithdrawalMethodCard(
                    icon = Icons.Default.AccountBalance,
                    methodName = "Transferencia Bancaria",
                    timeEstimate = "1- 3 días hábiles",
                    isSelected = selectedMethod == 0,
                    onClick = { selectedMethod = 0 }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Visa / Mastercard
                WithdrawalMethodCard(
                    icon = Icons.Default.CreditCard,
                    methodName = "Visa / Mastercard",
                    timeEstimate = "Instantáneo",
                    isSelected = selectedMethod == 1,
                    onClick = { selectedMethod = 1 }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Criptomonedas
                WithdrawalMethodCard(
                    icon = Icons.Default.CreditCard, // Placeholder - usuario cambiará por icono de cripto
                    methodName = "Criptomonedas",
                    timeEstimate = "~ 15 minutos",
                    isSelected = selectedMethod == 2,
                    onClick = { selectedMethod = 2 }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------------------------------------------------------------
                // BOTÓN RETIRAR FONDOS
                // ------------------------------------------------------------------
                RedButton(
                    text = "RETIRAR FONDOS",
                    icon = null,
                    onClick = {
                        // Resetear mensajes
                        errorMessage = null
                        showSuccess = false

                        // Validar que se haya ingresado un monto
                        val amount = withdrawAmount.toDoubleOrNull()
                        if (amount == null || amount <= 0) {
                            errorMessage = "Por favor ingresa un monto válido"
                            return@RedButton
                        }

                        // Validar monto mínimo
                        if (amount < minWithdrawal) {
                            errorMessage = "El monto mínimo es $${String.format("%.2f", minWithdrawal)}"
                            return@RedButton
                        }

                        // Validar fondos suficientes
                        if (!balanceViewModel.hasSufficientFunds(amount)) {
                            errorMessage = "Fondos insuficientes. Saldo actual: ${formattedBalance}"
                            return@RedButton
                        }

                        // Procesar retiro
                        val success = balanceViewModel.withdraw(
                            amount = amount,
                            method = methodNames[selectedMethod]
                        )

                        if (success) {
                            showSuccess = true
                            withdrawAmount = ""
                            
                            // Opcionalmente, navegar de vuelta después de 2 segundos
                            // kotlinx.coroutines.GlobalScope.launch {
                            //     kotlinx.coroutines.delay(2000)
                            //     navController.popBackStack()
                            // }
                        } else {
                            errorMessage = "Error al procesar el retiro. Inténtalo nuevamente."
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // ------------------------------------------------------------------
            // FOOTER DE NAVEGACIÓN
            // ------------------------------------------------------------------
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WithdrawScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        WithdrawScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
