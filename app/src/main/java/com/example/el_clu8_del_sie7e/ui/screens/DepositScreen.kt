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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AmountChip
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.PrimaryButton
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.SurfaceDark

/**
 * =====================================================================================
 * DEPOSITSCREEN.KT - PANTALLA DE DEPÓSITO DE FONDOS
 * =====================================================================================
 *
 * Esta pantalla permite a los usuarios depositar fondos en su cuenta del casino.
 *
 * FUNCIONALIDADES:
 * ----------------
 * - Selección de método de pago (Tarjeta, Transferencia, E-Wallet)
 * - Input de monto a depositar
 * - Chips de montos rápidos (+$50, +$100, +$500, +$1000)
 * - Formulario de datos de tarjeta
 * - Header con saldo actual
 * - Footer de navegación
 *
 * COMPONENTES USADOS:
 * -------------------
 * - AppHeader: Muestra el logo y saldo del usuario
 * - AppFooter: Barra de navegación inferior
 * - AmountChip: Chips para selección rápida de montos
 * - PrimaryButton: Botón de acción principal
 *
 * =====================================================================================
 */

// Enum para los métodos de pago
enum class PaymentMethod {
    CARD, TRANSFER, EWALLET
}

@Composable
fun DepositScreen(
    navController: NavController
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CARD) }
    var depositAmount by remember { mutableStateOf("0.00") }
    var selectedAmountChip by remember { mutableStateOf("+\$500") }
    var cardNumber by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var selectedFooterItem by remember { mutableStateOf("Cartera") }

    // Lista de montos rápidos
    val quickAmounts = listOf("+\$50", "+\$100", "+\$500", "+\$1000")

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ===================================================================
            // HEADER CON SALDO
            // ===================================================================
            AppHeader(
                balance = "$5,000.00",
                navController = navController
            )

            // ===================================================================
            // CONTENIDO PRINCIPAL (scrollable)
            // ===================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 21.dp, vertical = 16.dp)
            ) {
                // ============================================================
                // SECCIÓN 1: TÍTULO Y BOTÓN VOLVER
                // ============================================================
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón volver
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = AccentGold,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Título
                    Text(
                        text = "DEPÓSITO",
                        color = AccentGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ============================================================
                // SECCIÓN 2: MÉTODO DE PAGO
                // ============================================================
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Método de Pago",
                        color = AccentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjetas de métodos de pago
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tarjeta de crédito/débito
                    PaymentMethodBox(
                        icon = Icons.Default.CreditCard,
                        label = "TARJETA",
                        selected = selectedPaymentMethod == PaymentMethod.CARD,
                        onClick = { selectedPaymentMethod = PaymentMethod.CARD },
                        modifier = Modifier.weight(1f)
                    )

                    // Transferencia bancaria
                    PaymentMethodBox(
                        icon = Icons.Default.AccountBalance,
                        label = "TRANSFERENCIA",
                        selected = selectedPaymentMethod == PaymentMethod.TRANSFER,
                        onClick = { selectedPaymentMethod = PaymentMethod.TRANSFER },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // E-Wallet (fila separada)
                PaymentMethodBox(
                    icon = Icons.Default.Wallet,
                    label = "E-WALLET",
                    selected = selectedPaymentMethod == PaymentMethod.EWALLET,
                    onClick = { selectedPaymentMethod = PaymentMethod.EWALLET },
                    modifier = Modifier.width(140.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // ============================================================
                // SECCIÓN 3: MONTO A DEPOSITAR
                // ============================================================
                Text(
                    text = "MONTO A DEPOSITAR",
                    color = AccentGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo de monto
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        color = SurfaceDark,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.5.dp,
                                        color = Color(0xFF777150),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$",
                                color = AccentGold,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            BasicTextField(
                                value = depositAmount,
                                onValueChange = { depositAmount = it },
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                cursorBrush = SolidColor(AccentGold),
                                modifier = Modifier.width(150.dp)
                            )
                        }

                        Text(
                            text = "MÁXIMO",
                            color = AccentGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                depositAmount = "10000.00"
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Chips de montos rápidos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickAmounts.forEach { amount ->
                        AmountChip(
                            amount = amount,
                            selected = selectedAmountChip == amount,
                            onClick = {
                                selectedAmountChip = amount
                                // Actualizar el monto en el campo
                                depositAmount = amount.replace("+\$", "") + ".00"
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ============================================================
                // SECCIÓN 4: DETALLES DE LA TARJETA
                // ============================================================
                if (selectedPaymentMethod == PaymentMethod.CARD) {
                    Text(
                        text = "DETALLES DE LA TARJETA",
                        color = AccentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Número de tarjeta
                    Text(
                        text = "NÚMERO DE TARJETA",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(
                                color = SurfaceDark,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.5.dp,
                                color = Color(0xFF777150),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = cardNumber,
                                onValueChange = { if (it.length <= 19) cardNumber = formatCardNumber(it) },
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                cursorBrush = SolidColor(AccentGold),
                                modifier = Modifier.weight(1f),
                                decorationBox = { innerTextField ->
                                    if (cardNumber.isEmpty()) {
                                        Text(
                                            text = "0000  0000  0000  0000",
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )

                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Expiración y CVV
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Expiración
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "EXPIRACIÓN",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        color = SurfaceDark,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFF4A4A4A),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                BasicTextField(
                                    value = expirationDate,
                                    onValueChange = { if (it.length <= 5) expirationDate = formatExpiration(it) },
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 16.sp
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    cursorBrush = SolidColor(AccentGold),
                                    decorationBox = { innerTextField ->
                                        if (expirationDate.isEmpty()) {
                                            Text(
                                                text = "MM/YY",
                                                color = Color.Gray,
                                                fontSize = 16.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                            }
                        }

                        // CVV
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "CVV",
                                color = Color.Gray,
                                fontSize = 11.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        color = SurfaceDark,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFF4A4A4A),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                BasicTextField(
                                    value = cvv,
                                    onValueChange = { if (it.length <= 4) cvv = it.filter { char -> char.isDigit() } },
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 16.sp
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    cursorBrush = SolidColor(AccentGold),
                                    decorationBox = { innerTextField ->
                                        if (cvv.isEmpty()) {
                                            Text(
                                                text = "***",
                                                color = Color.Gray,
                                                fontSize = 16.sp
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de depositar
                    PrimaryButton(
                        text = "DEPOSITAR",
                        onClick = {
                            // TODO: Procesar el depósito
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // ===================================================================
            // FOOTER DE NAVEGACIÓN
            // ===================================================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

/**
 * Componente interno para las tarjetas de método de pago
 */
@Composable
private fun PaymentMethodBox(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .then(
                if (selected) {
                    Modifier.background(
                        color = Color(0xFF6D0000),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                        .background(
                            color = SurfaceDark,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF777150),
                            shape = RoundedCornerShape(12.dp)
                        )
                }
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) AccentGold else Color.Gray,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Check de selección
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(
                        color = Color(0xFF4CAF50),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Seleccionado",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

/**
 * Formatea el número de tarjeta agregando espacios cada 4 dígitos
 */
private fun formatCardNumber(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    val formatted = StringBuilder()
    for (i in digitsOnly.indices) {
        if (i > 0 && i % 4 == 0) {
            formatted.append("  ")
        }
        formatted.append(digitsOnly[i])
    }
    return formatted.toString()
}

/**
 * Formatea la fecha de expiración en formato MM/YY
 */
private fun formatExpiration(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    return when {
        digitsOnly.length <= 2 -> digitsOnly
        else -> "${digitsOnly.take(2)}/${digitsOnly.drop(2).take(2)}"
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true)
@Composable
fun DepositScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        DepositScreen(navController = rememberNavController())
    }
}
