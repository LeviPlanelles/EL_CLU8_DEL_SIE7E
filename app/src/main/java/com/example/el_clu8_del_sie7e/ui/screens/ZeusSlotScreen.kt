package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.SlotMachine
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.RegisterBackground
import com.example.el_clu8_del_sie7e.viewmodel.SpinResult
import com.example.el_clu8_del_sie7e.viewmodel.ZeusSlotViewModel
import kotlinx.coroutines.launch

/**
 * =====================================================================================
 * ZEUSSLOTSCREEN.KT - PANTALLA DEL JUEGO ZEUS SLOT
 * =====================================================================================
 *
 * Pantalla completa del juego de tragaperras Zeus Slot.
 *
 * FUNCIONALIDADES:
 * ---------------
 * - Máquina tragaperras de 5 rodillos con animación
 * - Sistema de apuestas con botones rápidos (1, 5, 50, 100)
 * - Botón GIRAR (centro)
 * - Botones + y - para ajustar apuesta
 * - Toggle AUTO-ROLL para giros automáticos
 * - Alertas de victoria/derrota
 * - Balance actualizado en tiempo real
 *
 * DISEÑO:
 * -------
 * - Fondo: RegisterBackground (gris oscuro)
 * - Header: Logo + Balance
 * - Título: "ZEUS SLOT" en dorado
 * - Apuesta y Ganancias con etiquetas doradas
 * - Botón GIRAR: Rojo con icono de reload
 * - Footer: Navegación inferior
 *
 * =====================================================================================
 */
@Composable
fun ZeusSlotScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ZeusSlotViewModel = viewModel()
) {
    // ===================================================================
    // ESTADO DE LA PANTALLA
    // ===================================================================
    var selectedFooterItem by remember { mutableStateOf("Juegos") }
    val balance by viewModel.balance.collectAsState()
    val currentBet by viewModel.currentBet.collectAsState()
    val winnings by viewModel.winnings.collectAsState()
    val reels by viewModel.reels.collectAsState()
    val isSpinning by viewModel.isSpinning.collectAsState()
    val spinState by viewModel.spinState.collectAsState()
    val resultMessage by viewModel.resultMessage.collectAsState()
    val autoRoll by viewModel.autoRoll.collectAsState()
    val scope = rememberCoroutineScope()

    // Estado para mostrar diálogo de resultado
    var showResultDialog by remember { mutableStateOf(false) }

    // Mostrar diálogo cuando cambie el estado del giro
    if (spinState == SpinResult.WIN || spinState == SpinResult.LOSE) {
        showResultDialog = true
    }

    // ===================================================================
    // UI DE LA PANTALLA
    // ===================================================================
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(RegisterBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ===================================================================
            // HEADER CON LOGO Y SALDO
            // ===================================================================
            AppHeader(
                balance = String.format("$%.2f", balance),
                navController = navController
            )

            // ===================================================================
            // CONTENIDO PRINCIPAL
            // ===================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Contenido superior con padding
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ============================================================
                    // SECCIÓN 1: TÍTULO Y BOTONES DE NAVEGACIÓN
                    // ============================================================
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón volver
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = AccentGold,
                            modifier = Modifier
                                .size(28.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                        )

                        // Título "ZEUS SLOT"
                        Text(
                            text = "ZEUS SLOT",
                            color = AccentGold,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )

                        // Botón de ayuda
                        Icon(
                            imageVector = Icons.Filled.HelpOutline,
                            contentDescription = "Ayuda",
                            tint = Color.Gray,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ============================================================
                    // SECCIÓN 2: MÁQUINA TRAGAPERRAS CON PALANCA
                    // ============================================================
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp), // Misma altura que la slot machine
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Máquina tragaperras
                        SlotMachine(
                            symbols = reels,
                            isSpinning = isSpinning,
                            modifier = Modifier.weight(1f)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        // Palanca roja al lado derecho
                        SlotLever(
                            isSpinning = isSpinning,
                            enabled = !isSpinning && currentBet > 0 && currentBet <= balance,
                            onClick = {
                                scope.launch {
                                    viewModel.spin()
                                }
                            },
                            modifier = Modifier.height(180.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }

                // ============================================================
                // SECCIÓN 3: PANEL DE CONTROLES ESTILO SLOT MACHINE CLÁSICO
                // ============================================================
                SlotControlPanel(
                    currentBet = currentBet,
                    winnings = winnings,
                    balance = balance,
                    autoRoll = autoRoll,
                    isSpinning = isSpinning,
                    onBetSelected = { viewModel.setBet(it) },
                    onIncrementBet = { viewModel.incrementBet(1.0) },
                    onDecrementBet = { viewModel.decrementBet(1.0) },
                    onToggleAutoRoll = { viewModel.toggleAutoRoll() }
                )
            }

            // ===================================================================
            // FOOTER DE NAVEGACIÓN
            // ===================================================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { item ->
                    selectedFooterItem = item
                },
                navController = navController
            )
        }

        // ===================================================================
        // DIÁLOGO DE RESULTADO
        // ===================================================================
        if (showResultDialog) {
            ResultDialog(
                isWin = spinState == SpinResult.WIN,
                message = resultMessage,
                onDismiss = {
                    showResultDialog = false
                    viewModel.resetResult()
                }
            )
        }
    }
}

/**
 * =====================================================================================
 * SLOTLEVER - PALANCA CLÁSICA DE TRAGAPERRAS (VERSIÓN SIMPLIFICADA)
 * =====================================================================================
 * 
 * Palanca clásica estilo casino con pomo rojo arriba y mango largo hasta abajo.
 * Versión simplificada sin animaciones complejas para evitar problemas de rendimiento.
 */
@Composable
private fun SlotLever(
    isSpinning: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animación simple de escala cuando está presionado
    var isPressed by remember { mutableStateOf(false) }
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isPressed || isSpinning) 0.95f else 1f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 100),
        label = "leverScale"
    )
    
    Box(
        modifier = modifier
            .width(60.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            // ============================================================
            // POMO ROJO (BOLA) - CLICKABLE
            // ============================================================
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.radialGradient(
                            colors = listOf(
                                if (isSpinning) Color(0xFF666666) else Color(0xFFFF7777),
                                if (isSpinning) Color(0xFF555555) else Color(0xFFFF4444),
                                if (isSpinning) Color(0xFF444444) else Color(0xFFE53935),
                                if (isSpinning) Color(0xFF333333) else Color(0xFFB71C1C),
                                if (isSpinning) Color(0xFF222222) else Color(0xFF8B0000)
                            ),
                            center = androidx.compose.ui.geometry.Offset(18f, 15f),
                            radius = 60f
                        ),
                        shape = CircleShape
                    )
                    .border(
                        width = 3.dp,
                        color = if (isSpinning) Color(0xFF333333) else Color(0xFF5D0000),
                        shape = CircleShape
                    )
                    .clickable(enabled = enabled) {
                        isPressed = true
                        onClick()
                        // Reset después de un momento
                        isPressed = false
                    }
            ) {
                // Brillo realista en la bola
                if (!isSpinning) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .offset(x = 10.dp, y = 8.dp)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.radialGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.8f),
                                        Color.White.copy(alpha = 0.3f),
                                        Color.Transparent
                                    ),
                                    radius = 30f
                                ),
                                shape = CircleShape
                            )
                    )
                }
            }
            
            // ============================================================
            // MANGO LARGO (PALANCA)
            // ============================================================
            Box(
                modifier = Modifier
                    .width(14.dp)
                    .weight(1f)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF3A3A3A),
                                Color(0xFF6E6E6E),
                                Color(0xFFAAAAAA),
                                Color(0xFF6E6E6E),
                                Color(0xFF3A3A3A)
                            )
                        ),
                        shape = RoundedCornerShape(7.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF2A2A2A),
                        shape = RoundedCornerShape(7.dp)
                    )
            )
        }
        
        // ============================================================
        // BASE/SOPORTE DE LA PALANCA (FIJA)
        // ============================================================
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(32.dp)
                .height(16.dp)
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF5A5A5A),
                            Color(0xFF3A3A3A),
                            Color(0xFF2A2A2A)
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = Color(0xFF1A1A1A),
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

/**
 * =====================================================================================
 * SLOTCONTROLPANEL - PANEL DE CONTROLES ESTILO SLOT MACHINE CLÁSICO
 * =====================================================================================
 * 
 * Panel inferior con diseño de máquina tragaperras clásica:
 * - Display LED central con APUESTA y GANANCIAS
 * - Fila de botones de apuesta rápida
 * - Controles +/- y AUTO-ROLL
 */
@Composable
private fun SlotControlPanel(
    currentBet: Double,
    winnings: Double,
    balance: Double,
    autoRoll: Boolean,
    isSpinning: Boolean,
    onBetSelected: (Double) -> Unit,
    onIncrementBet: () -> Unit,
    onDecrementBet: () -> Unit,
    onToggleAutoRoll: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Opciones de apuesta disponibles
    val betOptions = listOf(1.0, 5.0, 10.0, 50.0, 100.0)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF0D0D0D)
                    )
                )
            )
            .border(
                width = 2.dp,
                color = Color(0xFF3A3A3A),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ============================================================
            // FILA 1: DISPLAYS LED DE APUESTA Y GANANCIAS
            // ============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Display de APUESTA
                LedDisplay(
                    label = "APUESTA",
                    value = currentBet,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                
                // Display de GANANCIAS
                LedDisplay(
                    label = "GANANCIAS",
                    value = winnings,
                    color = AccentGold,
                    modifier = Modifier.weight(1f)
                )
            }
            
            // ============================================================
            // FILA 2: BOTONES DE APUESTA RÁPIDA EN LÍNEA
            // ============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                betOptions.forEach { betAmount ->
                    BetChip(
                        amount = betAmount,
                        isSelected = currentBet == betAmount,
                        enabled = !isSpinning && betAmount <= balance,
                        onClick = { onBetSelected(betAmount) },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Botón MAX
                BetChip(
                    amount = balance,
                    isMax = true,
                    isSelected = currentBet == balance,
                    enabled = !isSpinning && balance > 0,
                    onClick = { onBetSelected(balance) },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // ============================================================
            // FILA 3: CONTROLES +/- Y AUTO-ROLL
            // ============================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón -
                ControlButtonLarge(
                    icon = Icons.Filled.Remove,
                    onClick = onDecrementBet,
                    enabled = !isSpinning && currentBet > 1.0
                )
                
                // Barra decorativa central
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF3A3A3A),
                                    AccentGold.copy(alpha = 0.5f),
                                    AccentGold,
                                    AccentGold.copy(alpha = 0.5f),
                                    Color(0xFF3A3A3A)
                                )
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                
                // Botón AUTO-ROLL
                AutoRollButton(
                    isActive = autoRoll,
                    onClick = onToggleAutoRoll
                )
                
                // Barra decorativa central
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF3A3A3A),
                                    AccentGold.copy(alpha = 0.5f),
                                    AccentGold,
                                    AccentGold.copy(alpha = 0.5f),
                                    Color(0xFF3A3A3A)
                                )
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                
                // Botón +
                ControlButtonLarge(
                    icon = Icons.Filled.Add,
                    onClick = onIncrementBet,
                    enabled = !isSpinning && currentBet < balance
                )
            }
        }
    }
}

/**
 * =====================================================================================
 * LEDDISPLAY - DISPLAY ESTILO LED PARA MOSTRAR VALORES
 * =====================================================================================
 */
@Composable
private fun LedDisplay(
    label: String,
    value: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFF0A0A0A),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF2A2A2A),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Etiqueta superior
        Text(
            text = label,
            color = color.copy(alpha = 0.7f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Valor con efecto LED
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF050505),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = color.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$${String.format("%.2f", value)}",
                color = color,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

/**
 * =====================================================================================
 * BETCHIP - FICHA DE APUESTA ESTILO CASINO
 * =====================================================================================
 */
@Composable
private fun BetChip(
    amount: Double,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    isMax: Boolean = false,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isSelected -> AccentGold
        !enabled -> Color(0xFF1A1A1A)
        else -> Color(0xFF2A2A2A)
    }
    
    val borderColor = when {
        isSelected -> AccentGold
        !enabled -> Color(0xFF2A2A2A)
        else -> Color(0xFF4A4A4A)
    }
    
    val textColor = when {
        isSelected -> Color.Black
        !enabled -> Color(0xFF4A4A4A)
        else -> Color.White
    }
    
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isMax) "MAX" else amount.toInt().toString(),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * =====================================================================================
 * CONTROLBUTTONLARGE - BOTÓN DE CONTROL GRANDE (+/-)
 * =====================================================================================
 */
@Composable
private fun ControlButtonLarge(
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(
                brush = if (enabled) {
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3A3A3A),
                            Color(0xFF2A2A2A)
                        )
                    )
                } else {
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A1A),
                            Color(0xFF1A1A1A)
                        )
                    )
                },
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                color = if (enabled) Color(0xFF5A5A5A) else Color(0xFF2A2A2A),
                shape = CircleShape
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) Color.White else Color(0xFF4A4A4A),
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * =====================================================================================
 * AUTOROLLBUTTON - BOTÓN DE AUTO-ROLL ESTILO TOGGLE
 * =====================================================================================
 */
@Composable
private fun AutoRollButton(
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (isActive) AccentGold else Color(0xFF2A2A2A),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 2.dp,
                color = if (isActive) AccentGold else Color(0xFF4A4A4A),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Indicador LED
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (isActive) Color(0xFF00FF00) else Color(0xFF4A4A4A),
                        shape = CircleShape
                    )
            )
            
            Text(
                text = "AUTO",
                color = if (isActive) Color.Black else Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}

/**
 * =====================================================================================
 * BETBUTTON - BOTÓN DE APUESTA RÁPIDA (Legacy, mantenido por compatibilidad)
 * =====================================================================================
 */
@Composable
private fun BetButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(35.dp)
            .background(
                color = if (isSelected) AccentGold else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 2.dp,
                color = if (isSelected) AccentGold else Color(0xFF6B6B6B),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * =====================================================================================
 * CONTROLBUTTON - BOTÓN DE CONTROL (+ y -)
 * =====================================================================================
 */
@Composable
private fun ControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(35.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF6B6B6B),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * =====================================================================================
 * RESULTDIALOG - DIÁLOGO DE RESULTADO DE LA TIRADA
 * =====================================================================================
 */
@Composable
private fun ResultDialog(
    isWin: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isWin) "¡FELICIDADES!" else "¡INTENTA DE NUEVO!",
                color = if (isWin) AccentGold else Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = message,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("CONTINUAR", color = AccentGold)
            }
        },
        containerColor = DarkBackground
    )
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true)
@Composable
fun ZeusSlotScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        ZeusSlotScreen(navController = rememberNavController())
    }
}
