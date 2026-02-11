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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AmountChip
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.PrimaryButton
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedCenter
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedEnd
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedStart
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.ui.theme.SurfaceDark
import com.example.el_clu8_del_sie7e.ui.theme.Tomorrow
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import java.util.Locale

/**
 * =====================================================================================
 * DEPOSITSCREEN.KT - PANTALLA DE DEPÓSITO DE FONDOS
 * =====================================================================================
 *
 * COMPORTAMIENTO DE SCROLL ESPECIAL:
 * ----------------------------------
 * 1. Estado inicial: AppHeader (logo+balance) arriba, luego "← DEPÓSITO" debajo
 * 2. Al hacer scroll hacia abajo: AppHeader se esconde, "← DEPÓSITO" sube y se 
 *    queda FIJO en el borde superior de la pantalla
 * 3. Al hacer scroll hacia arriba: AppHeader vuelve a aparecer y todo vuelve
 *    a su posición original
 *
 * Esto se logra usando LazyColumn con stickyHeader para el título "DEPÓSITO"
 *
 * =====================================================================================
 */

enum class PaymentMethod {
    CARD, TRANSFER, EWALLET
}

@Composable
fun DepositScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CARD) }
    var depositAmount by remember { mutableStateOf("") }
    var selectedAmountChip by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var selectedFooterItem by remember { mutableStateOf("Cartera") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    // Obtener balance actual del ViewModel
    val currentBalance by balanceViewModel.balance.collectAsState()
    val formattedBalance = balanceViewModel.formatBalance(currentBalance)

    val quickAmounts = listOf("+\$50", "+\$100", "+\$500", "+\$1000")
    
    val methodName = when (selectedPaymentMethod) {
        PaymentMethod.CARD -> "Tarjeta"
        PaymentMethod.TRANSFER -> "Transferencia"
        PaymentMethod.EWALLET -> "E-Wallet"
    }

    val redGradient = Brush.horizontalGradient(
        colors = listOf(ButtonRedStart, ButtonRedCenter, ButtonRedEnd)
    )

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ==========================================
            // CONTENIDO CON LAZY COLUMN Y STICKY HEADER
            // ==========================================
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // ==========================================
                // ITEM 1: AppHeader (Logo + Balance) - SCROLLABLE
                // ==========================================
                item {
                    AppHeader(balance = formattedBalance, navController = navController)
                }

                // ==========================================
                // STICKY HEADER: Título "← DEPÓSITO" - SE QUEDA FIJO ARRIBA
                // ==========================================
                stickyHeader {
                    DepositStickyHeader(
                        onBackClick = { navController.popBackStack() }
                    )
                }

                // ==========================================
                // ITEM 2: Contenido del formulario
                // ==========================================
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 21.dp, vertical = 16.dp)
                    ) {
                        // SECCIÓN MÉTODO DE PAGO
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CreditCard, null, tint = AccentGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Método de Pago", color = AccentGold, fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Fila 1: Tarjeta y Transferencia
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            PaymentMethodBox(
                                icon = Icons.Default.CreditCard,
                                label = "TARJETA",
                                selected = selectedPaymentMethod == PaymentMethod.CARD,
                                onClick = { selectedPaymentMethod = PaymentMethod.CARD },
                                modifier = Modifier.weight(1f)
                            )
                            PaymentMethodBox(
                                icon = Icons.Default.AccountBalance,
                                label = "TRANSFERENCIA",
                                selected = selectedPaymentMethod == PaymentMethod.TRANSFER,
                                onClick = { selectedPaymentMethod = PaymentMethod.TRANSFER },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Fila 2: E-Wallet
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            PaymentMethodBox(
                                icon = Icons.Default.Wallet,
                                label = "E-WALLET",
                                selected = selectedPaymentMethod == PaymentMethod.EWALLET,
                                onClick = { selectedPaymentMethod = PaymentMethod.EWALLET },
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // SECCIÓN MONTO A DEPOSITAR
                        Text("MONTO A DEPOSITAR", color = AccentGold, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(color = SurfaceDark, shape = RoundedCornerShape(8.dp))
                                .border(width = 1.5.dp, color = Color(0xFF777150), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Símbolo $ con fuente Tomorrow para números
                                    Text(
                                        text = "$",
                                        color = AccentGold,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Tomorrow
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    // Campo de entrada de monto con fuente Tomorrow
                                    BasicTextField(
                                        value = depositAmount,
                                        onValueChange = { depositAmount = it },
                                        textStyle = TextStyle(
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontFamily = Tomorrow
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        cursorBrush = SolidColor(AccentGold),
                                        modifier = Modifier.width(150.dp)
                                    )
                                }
                                Text("MÁXIMO", color = AccentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins, modifier = Modifier.clickable { 
                                    depositAmount = "10000.00"
                                    errorMessage = null
                                    showSuccess = false
                                })
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
                                        val currentVal = depositAmount.toDoubleOrNull() ?: 0.0
                                        val addedVal = amount.replace("+\$", "").toDoubleOrNull() ?: 0.0
                                        depositAmount = String.format(Locale.US, "%.2f", currentVal + addedVal)
                                    },
                                    selectedBrush = redGradient,
                                    selectedTextColor = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // SECCIÓN DETALLES DE TARJETA
                        if (selectedPaymentMethod == PaymentMethod.CARD) {
                            Text("DETALLES DE LA TARJETA", color = AccentGold, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Poppins)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("NÚMERO DE TARJETA", color = Color.Gray, fontSize = 11.sp, fontFamily = Poppins)
                            Spacer(modifier = Modifier.height(6.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(color = SurfaceDark, shape = RoundedCornerShape(8.dp))
                                    .border(width = 1.5.dp, color = Color(0xFF777150), shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    BasicTextField(
                                        value = cardNumber,
                                        onValueChange = { if (it.length <= 19) cardNumber = formatCardNumber(it) },
                                        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        cursorBrush = SolidColor(AccentGold),
                                        modifier = Modifier.weight(1f),
                                        decorationBox = { innerTextField ->
                                            if (cardNumber.isEmpty()) Text("0000  0000  0000  0000", color = Color.Gray, fontSize = 16.sp)
                                            innerTextField()
                                        }
                                    )
                                    Icon(Icons.Default.CreditCard, null, tint = AccentGold, modifier = Modifier.size(24.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("EXPIRACIÓN", color = Color.Gray, fontSize = 11.sp, fontFamily = Poppins)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Box(modifier = Modifier.fillMaxWidth().height(50.dp).background(color = SurfaceDark, shape = RoundedCornerShape(8.dp)).border(width = 1.dp, color = Color(0xFF4A4A4A), shape = RoundedCornerShape(8.dp)).padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) {
                                        BasicTextField(
                                            value = expirationDate,
                                            onValueChange = { if (it.length <= 5) expirationDate = formatExpiration(it) },
                                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            cursorBrush = SolidColor(AccentGold),
                                            decorationBox = { if (expirationDate.isEmpty()) Text("MM/YY", color = Color.Gray, fontSize = 16.sp); it() }
                                        )
                                    }
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("CVV", color = Color.Gray, fontSize = 11.sp, fontFamily = Poppins)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Box(modifier = Modifier.fillMaxWidth().height(50.dp).background(color = SurfaceDark, shape = RoundedCornerShape(8.dp)).border(width = 1.dp, color = Color(0xFF4A4A4A), shape = RoundedCornerShape(8.dp)).padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) {
                                        BasicTextField(
                                            value = cvv,
                                            onValueChange = { if (it.length <= 4) cvv = it.filter { c -> c.isDigit() } },
                                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            cursorBrush = SolidColor(AccentGold),
                                            decorationBox = { if (cvv.isEmpty()) Text("***", color = Color.Gray, fontSize = 16.sp); it() }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Botón para generar tarjeta aleatoria
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = AccentGold.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = AccentGold.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        cardNumber = balanceViewModel.generateRandomCardNumber()
                                        expirationDate = balanceViewModel.generateRandomExpiry()
                                        cvv = balanceViewModel.generateRandomCVV()
                                        errorMessage = null
                                    }
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "GENERAR TARJETA ALEATORIA",
                                    color = AccentGold,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Poppins
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Mostrar mensaje de error
                            if (errorMessage != null) {
                                Text(
                                    text = errorMessage!!,
                                    color = Color(0xFFFF5252),
                                    fontSize = 13.sp,
                                    fontFamily = Poppins,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            // Mostrar mensaje de éxito con balance en fuente Tomorrow
                            if (showSuccess) {
                                Row {
                                    Text(
                                        text = "Depósito exitoso. Nuevo balance: ",
                                        color = Color(0xFF00C853),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Poppins
                                    )
                                    Text(
                                        text = formattedBalance,
                                        color = Color(0xFF00C853),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = Tomorrow
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            PrimaryButton(
                                text = "DEPOSITAR",
                                onClick = {
                                    errorMessage = null
                                    showSuccess = false

                                    val amount = depositAmount.toDoubleOrNull()
                                    if (amount == null || amount <= 0) {
                                        errorMessage = "Por favor ingresa un monto válido"
                                        return@PrimaryButton
                                    }

                                    if (selectedPaymentMethod == PaymentMethod.CARD) {
                                        if (cardNumber.isEmpty() || cardNumber.replace(" ", "").length < 16) {
                                            errorMessage = "Por favor ingresa un número de tarjeta válido"
                                            return@PrimaryButton
                                        }
                                        if (expirationDate.isEmpty() || expirationDate.length < 5) {
                                            errorMessage = "Por favor ingresa una fecha de expiración válida"
                                            return@PrimaryButton
                                        }
                                        if (cvv.isEmpty() || cvv.length < 3) {
                                            errorMessage = "Por favor ingresa un CVV válido"
                                            return@PrimaryButton
                                        }
                                    }

                                    val success = balanceViewModel.deposit(
                                        amount = amount,
                                        method = methodName
                                    )

                                    if (success) {
                                        showSuccess = true
                                        depositAmount = ""
                                        cardNumber = ""
                                        expirationDate = ""
                                        cvv = ""
                                        selectedAmountChip = ""
                                    } else {
                                        errorMessage = "Error al procesar el depósito. Inténtalo nuevamente."
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                brush = redGradient,
                                textColor = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // ==========================================
            // FOOTER FIJO
            // ==========================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

/**
 * Header sticky con el título "← DEPÓSITO".
 * Se queda fijo en la parte superior cuando el usuario hace scroll hacia abajo.
 */
@Composable
private fun DepositStickyHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBackground)  // Fondo sólido para tapar el contenido que pasa por debajo
            .padding(horizontal = 21.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = AccentGold,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "DEPÓSITO",
            color = AccentGold,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )
        }
    }
}

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
            .background(
                color = if (selected) Color(0xFF6D0000) else SurfaceDark,
                shape = RoundedCornerShape(12.dp)
            )
            .then(
                if (!selected) Modifier.border(1.5.dp, Color(0xFF777150), RoundedCornerShape(12.dp))
                else Modifier
            )
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Icon(icon, label, tint = if (selected) AccentGold else Color.Gray, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium, fontFamily = Poppins)
        }
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(20.dp)
                    .background(Color(0xFF4CAF50), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
            }
        }
    }
}

private fun formatCardNumber(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    val formatted = StringBuilder()
    for (i in digitsOnly.indices) {
        if (i > 0 && i % 4 == 0) formatted.append("  ")
        formatted.append(digitsOnly[i])
    }
    return formatted.toString()
}

private fun formatExpiration(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    return when {
        digitsOnly.length <= 2 -> digitsOnly
        else -> "${digitsOnly.take(2)}/${digitsOnly.drop(2).take(2)}"
    }
}

@Preview(showBackground = true)
@Composable
fun DepositScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        DepositScreen(
            navController = rememberNavController(),
            balanceViewModel = BalanceViewModel()
        )
    }
}
