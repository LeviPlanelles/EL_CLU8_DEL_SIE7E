package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import com.example.el_clu8_del_sie7e.viewmodel.GameState
import com.example.el_clu8_del_sie7e.viewmodel.SlotGameViewModel
import com.example.el_clu8_del_sie7e.viewmodel.SlotSymbol

/**
 * =====================================================================================
 * SLOTGAMEPLAYSCREEN.KT - PANTALLA DE JUEGO DE SLOT MACHINE
 * =====================================================================================
 *
 * Esta pantalla muestra el juego de slot machine interactivo "Zeus Slot".
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - 5 columnas x 3 filas de símbolos con animación de giro
 * - Sistema de apuestas con validación de saldo
 * - Detección de victorias (3, 4 o 5 símbolos iguales)
 * - Auto-roll con multiplicadores x3, x5, x10
 * - Feedback visual para victorias y derrotas
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario
 * - Título del juego con botón atrás y ayuda
 * - Máquina de slots: 5 carriles x 3 filas
 * - Palanca roja para girar
 * - Sección de apuesta: chips y control de cantidad
 * - Auto-roll: multiplicadores de tiradas automáticas
 * - AppFooter: Navegación inferior
 *
 * =====================================================================================
 */

// =====================================================================================
// COLORES ESPECÍFICOS DE ESTA PANTALLA
// =====================================================================================
private val SlotMachineBg = Color(0xFF1A1A1A)       // Fondo de la máquina
private val SlotReelBg = Color(0xFF0D0D0D)          // Fondo de cada carril
private val SlotBorderGold = Color(0xFFB8960C)      // Borde dorado de la máquina
private val LeverRed = Color(0xFFE53935)            // Rojo de la palanca
private val LeverRedDark = Color(0xFFB71C1C)        // Rojo oscuro para sombra
private val ChipSelectedBg = Color(0xFFD4AF37)      // Fondo chip seleccionado (dorado)
private val ChipUnselectedBg = Color.Transparent    // Fondo chip no seleccionado
private val ChipBorder = Color(0xFF4A4A4A)          // Borde de chips no seleccionados
private val BetControlBg = Color(0xFF2A2A2A)        // Fondo del control de apuesta
private val WinGreen = Color(0xFF4CAF50)            // Verde para victorias
private val LoseRed = Color(0xFFE53935)             // Rojo para derrotas

// Colores de los símbolos
private val SymbolYellow = Color(0xFFFFD54F)        // Amarillo para estrella
private val SymbolRed = Color(0xFFE53935)           // Rojo para corazón
private val SymbolBlue = Color(0xFF42A5F5)          // Azul para rayo
private val SymbolCyan = Color(0xFF4DD0E1)          // Cyan para diamante
private val SymbolWhite = Color(0xFFEEEEEE)         // Blanco para templo

@Composable
fun SlotGamePlayScreen(
    navController: NavController,
    slotName: String,
    balanceViewModel: BalanceViewModel,
    slotGameViewModel: SlotGameViewModel,
    modifier: Modifier = Modifier
) {
    // =========================================================================
    // ESTADOS DEL VIEWMODEL
    // =========================================================================
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()
    val gameState by slotGameViewModel.gameState.collectAsState()
    val currentBet by slotGameViewModel.currentBet.collectAsState()
    val selectedChip by slotGameViewModel.selectedChip.collectAsState()
    val autoRollMultiplier by slotGameViewModel.autoRollMultiplier.collectAsState()
    val autoRollRemaining by slotGameViewModel.autoRollRemaining.collectAsState()
    val reels by slotGameViewModel.reels.collectAsState()
    val revealedColumns by slotGameViewModel.revealedColumns.collectAsState()
    val message by slotGameViewModel.message.collectAsState()
    val lastWinAmount by slotGameViewModel.lastWinAmount.collectAsState()
    val lastSpinResult by slotGameViewModel.lastSpinResult.collectAsState()

    // Estado para controlar la edición manual del campo de apuesta
    var betText by remember(currentBet) { mutableStateOf(currentBet.toString()) }

    // Estado para mostrar/ocultar el diálogo de ayuda
    var showHelpDialog by remember { mutableStateOf(false) }

    // Determinar si el juego está activo (girando o revelando)
    val isGameActive = gameState == GameState.SPINNING || gameState == GameState.REVEALING

    // =========================================================================
    // DIÁLOGO DE AYUDA
    // =========================================================================
    if (showHelpDialog) {
        HelpDialog(
            slotName = slotName,
            onDismiss = { showHelpDialog = false }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ===================================================================
            // 1. HEADER CON BALANCE
            // ===================================================================
            AppHeader(
                balance = formattedBalance,
                navController = navController
            )

            // ===================================================================
            // 2. CONTENIDO SCROLLABLE
            // ===================================================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ---------------------------------------------------------------
                // 2.1 TÍTULO DEL JUEGO
                // ---------------------------------------------------------------
                SlotGameHeader(
                    title = slotName.uppercase(),
                    onBackClick = { 
                        slotGameViewModel.stopAutoRoll()
                        navController.popBackStack() 
                    },
                    onHelpClick = { showHelpDialog = true }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ---------------------------------------------------------------
                // 2.2 MENSAJE DE RESULTADO (Victoria/Derrota)
                // ---------------------------------------------------------------
                ResultMessage(
                    message = message,
                    isWin = gameState == GameState.WIN,
                    winAmount = lastWinAmount
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ---------------------------------------------------------------
                // 2.3 MÁQUINA DE SLOTS CON PALANCA
                // ---------------------------------------------------------------
                SlotMachineWithLever(
                    reels = reels,
                    revealedColumns = revealedColumns,
                    isSpinning = isGameActive,
                    winLines = lastSpinResult?.winLines?.map { it.rowIndex } ?: emptyList(),
                    showWinHighlight = gameState == GameState.WIN,
                    onLeverPull = {
                        if (autoRollMultiplier > 0) {
                            slotGameViewModel.startAutoRoll()
                        } else {
                            slotGameViewModel.spin()
                        }
                    },
                    enabled = !isGameActive && slotGameViewModel.canSpin()
                )

                // Mostrar tiradas restantes de auto-roll
                if (autoRollRemaining > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tiradas restantes: $autoRollRemaining",
                        color = AccentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ---------------------------------------------------------------
                // 2.4 SECCIÓN "TU APUESTA"
                // ---------------------------------------------------------------
                BetSection(
                    selectedChip = selectedChip,
                    onChipSelected = { chip -> slotGameViewModel.selectChip(chip) },
                    currentBet = currentBet,
                    betText = betText,
                    onBetTextChange = { newText ->
                        betText = newText
                        newText.toIntOrNull()?.let { slotGameViewModel.setBet(it) }
                    },
                    onBetIncrease = { slotGameViewModel.increaseBet() },
                    onBetDecrease = { slotGameViewModel.decreaseBet() },
                    selectedMultiplier = autoRollMultiplier,
                    onMultiplierSelected = { mult -> slotGameViewModel.toggleAutoRollMultiplier(mult) },
                    onAutoRollClick = {
                        if (autoRollRemaining > 0) {
                            slotGameViewModel.stopAutoRoll()
                        } else if (autoRollMultiplier > 0) {
                            slotGameViewModel.startAutoRoll()
                        }
                    },
                    isAutoRollActive = autoRollRemaining > 0,
                    enabled = !isGameActive
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // ===================================================================
            // 3. FOOTER
            // ===================================================================
            AppFooter(
                selectedItem = "Mesas",
                onItemSelected = { /* Navegación footer */ },
                navController = navController
            )
        }
    }
}

// =====================================================================================
// COMPONENTES DE LA PANTALLA
// =====================================================================================

/**
 * Mensaje de resultado que muestra victoria o derrota con animación espectacular
 * Diseño más grande y visible con efectos visuales llamativos
 */
@Composable
private fun ResultMessage(
    message: String,
    isWin: Boolean,
    winAmount: Double
) {
    // Animación de opacidad para fade in/out
    val alpha by animateFloatAsState(
        targetValue = if (message.isNotEmpty()) 1f else 0f,
        animationSpec = tween(400),
        label = "result_alpha"
    )
    
    // Animación de escala para efecto de "pop" más pronunciado
    val scale by animateFloatAsState(
        targetValue = if (message.isNotEmpty()) 1f else 0.5f,
        animationSpec = tween(400),
        label = "result_scale"
    )

    // Contenedor con altura fija para reservar espacio siempre
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), // Altura aumentada para mejor visibilidad
        contentAlignment = Alignment.Center
    ) {
        // Solo mostrar el contenido si hay mensaje
        if (message.isNotEmpty()) {
            if (isWin) {
                // =====================================================
                // DISEÑO DE VICTORIA - Espectacular y dorado
                // =====================================================
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scale)
                        .alpha(alpha)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2E7D32), // Verde oscuro arriba
                                    Color(0xFF4CAF50), // Verde medio
                                    Color(0xFF2E7D32)  // Verde oscuro abajo
                                )
                            )
                        )
                        .border(
                            width = 3.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    AccentGold,
                                    Color(0xFFFFE082), // Dorado claro
                                    AccentGold
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Título "¡GANASTE!" con estilo dorado
                        Text(
                            text = "🎉 ¡GANASTE! 🎉",
                            color = AccentGold,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // Cantidad ganada grande y prominente
                        if (winAmount > 0) {
                            Text(
                                text = "+${String.format("%.2f", winAmount)} €",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                // =====================================================
                // DISEÑO DE DERROTA - Discreto pero visible
                // =====================================================
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Más pequeño que victoria
                        .scale(scale)
                        .alpha(alpha)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF424242), // Gris oscuro
                                    Color(0xFF616161), // Gris medio
                                    Color(0xFF424242)  // Gris oscuro
                                )
                            )
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFF757575),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 14.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin premio",
                        color = Color(0xFFBDBDBD),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Header del juego con título, botón atrás y botón de ayuda
 */
@Composable
private fun SlotGameHeader(
    title: String,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Botón Atrás (izquierda)
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        // Título centrado
        Text(
            text = title,
            color = AccentGold,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        // Botón Ayuda (derecha)
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clip(CircleShape)
                .clickable { onHelpClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "?",
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Máquina de slots con la palanca a la derecha
 */
@Composable
private fun SlotMachineWithLever(
    reels: List<List<SlotSymbol>>,
    revealedColumns: Int,
    isSpinning: Boolean,
    winLines: List<Int>,
    showWinHighlight: Boolean,
    onLeverPull: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Máquina de slots
        SlotMachine(
            reels = reels,
            revealedColumns = revealedColumns,
            isSpinning = isSpinning,
            winLines = winLines,
            showWinHighlight = showWinHighlight
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Palanca
        SlotLever(
            onClick = onLeverPull,
            enabled = enabled,
            isSpinning = isSpinning
        )
    }
}

/**
 * Máquina de slots con 5 carriles y 3 filas
 * Diseño premium con separadores verticales y líneas doradas horizontales
 */
@Composable
private fun SlotMachine(
    reels: List<List<SlotSymbol>>,
    revealedColumns: Int,
    isSpinning: Boolean,
    winLines: List<Int>,
    showWinHighlight: Boolean
) {
    // Contenedor principal de la máquina con borde redondeado
    Box(
        modifier = Modifier
            .width(290.dp)
            .height(360.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF0D0D0D),
                        Color(0xFF1A1A1A)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3A3A3A),
                        Color(0xFF2A2A2A),
                        Color(0xFF3A3A3A)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        // Efecto de brillo interior sutil
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
                .clip(RoundedCornerShape(21.dp))
                .background(Color(0xFF0A0A0A))
        ) {
            // Grid de símbolos con separadores
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Fila superior decorativa con "7"s semitransparentes
                DecorationRow()
                
                // Separador dorado superior
                GoldenSeparator()
                
                // Fila principal de símbolos (la del medio es la importante)
                reels.forEachIndexed { rowIndex, row ->
                    val isWinningRow = showWinHighlight && winLines.contains(rowIndex)
                    val isMainRow = rowIndex == 1 // Fila central destacada
                    
                    // Fila de símbolos con separadores verticales
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .then(
                                if (isWinningRow) {
                                    Modifier
                                        .background(
                                            WinGreen.copy(alpha = 0.15f),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .border(
                                            width = 2.dp,
                                            color = WinGreen,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                } else Modifier
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            row.forEachIndexed { colIndex, symbol ->
                                // Separador vertical izquierdo (excepto primera columna)
                                if (colIndex > 0) {
                                    VerticalDivider(
                                        modifier = Modifier
                                            .height(if (isMainRow) 70.dp else 50.dp)
                                            .width(1.dp),
                                        color = Color(0xFF2A2A2A)
                                    )
                                }
                                
                                // Símbolo
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val isRevealed = colIndex < revealedColumns
                                    SlotSymbolView(
                                        symbol = symbol,
                                        isRevealed = isRevealed,
                                        isSpinning = isSpinning && !isRevealed,
                                        isWinning = isWinningRow,
                                        isMainRow = isMainRow
                                    )
                                }
                            }
                        }
                    }
                    
                    // Línea dorada separadora entre filas (no después de la última)
                    if (rowIndex < reels.size - 1) {
                        GoldenSeparator()
                    }
                }
                
                // Separador dorado inferior
                GoldenSeparator()
                
                // Fila inferior decorativa con "7"s semitransparentes
                DecorationRow()
            }
        }
    }
}

/**
 * Fila decorativa con "7"s semitransparentes
 */
@Composable
private fun DecorationRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Text(
                text = "7",
                color = Color(0xFF2A2A2A),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Separador dorado horizontal con gradiente
 */
@Composable
private fun GoldenSeparator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        AccentGold.copy(alpha = 0.6f),
                        AccentGold.copy(alpha = 0.8f),
                        AccentGold.copy(alpha = 0.6f),
                        Color.Transparent
                    )
                )
            )
    )
}

/**
 * Vista de un símbolo individual con animación
 * Diseño premium con círculos de fondo para los símbolos
 */
@Composable
private fun SlotSymbolView(
    symbol: SlotSymbol,
    isRevealed: Boolean,
    isSpinning: Boolean,
    isWinning: Boolean,
    isMainRow: Boolean = false
) {
    // Animación de escala para símbolos ganadores
    val scale by animateFloatAsState(
        targetValue = if (isWinning) 1.15f else 1f,
        animationSpec = tween(300),
        label = "symbol_scale"
    )

    // Tamaño adaptativo según si es fila principal o decorativa
    val symbolSize = if (isMainRow) 52.dp else 44.dp
    val iconSize = if (isMainRow) 40.dp else 34.dp
    val fontSize = if (isMainRow) 24.sp else 20.sp

    Box(
        modifier = Modifier
            .size(symbolSize)
            .scale(scale)
            .then(
                if (isWinning) {
                    Modifier.border(2.dp, AccentGold, CircleShape)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isSpinning) {
            // Efecto de giro - mostrar "?" con animación
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "?",
                    color = Color(0xFF3A3A3A),
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            // Mostrar símbolo real con diseño circular
            when (symbol) {
                SlotSymbol.STAR -> {
                    // Estrella con fondo circular amarillo/dorado
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFFE082),
                                        Color(0xFFFFD54F),
                                        Color(0xFFFFC107)
                                    )
                                )
                            )
                            .border(2.dp, Color(0xFFFF8F00), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "★",
                            color = Color(0xFF5D4037),
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                SlotSymbol.HEART -> {
                    // Corazón con fondo circular rojo
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFEF5350),
                                        Color(0xFFE53935),
                                        Color(0xFFC62828)
                                    )
                                )
                            )
                            .border(2.dp, Color(0xFFB71C1C), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "♥",
                            color = Color.White,
                            fontSize = fontSize
                        )
                    }
                }
                SlotSymbol.BOLT -> {
                    // Rayo azul eléctrico (sin fondo circular, más impactante)
                    Text(
                        text = "⚡",
                        color = Color(0xFF42A5F5),
                        fontSize = (fontSize.value + 6).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                SlotSymbol.TEMPLE -> {
                    // Templo/columnas griegas
                    Canvas(modifier = Modifier.size(iconSize)) {
                        val templeColor = Color(0xFFECEFF1)
                        val shadowColor = Color(0xFFB0BEC5)
                        
                        // Base
                        drawRoundRect(
                            color = templeColor,
                            topLeft = Offset(size.width * 0.1f, size.height * 0.8f),
                            size = Size(size.width * 0.8f, size.height * 0.12f),
                            cornerRadius = CornerRadius(2f)
                        )
                        
                        // Columnas (3)
                        listOf(0.18f, 0.44f, 0.70f).forEach { xPos ->
                            drawRoundRect(
                                color = shadowColor,
                                topLeft = Offset(size.width * (xPos + 0.02f), size.height * 0.38f),
                                size = Size(size.width * 0.12f, size.height * 0.44f),
                                cornerRadius = CornerRadius(2f)
                            )
                            drawRoundRect(
                                color = templeColor,
                                topLeft = Offset(size.width * xPos, size.height * 0.35f),
                                size = Size(size.width * 0.12f, size.height * 0.45f),
                                cornerRadius = CornerRadius(2f)
                            )
                        }
                        
                        // Techo triangular
                        val roofPath = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.08f)
                            lineTo(size.width * 0.05f, size.height * 0.35f)
                            lineTo(size.width * 0.95f, size.height * 0.35f)
                            close()
                        }
                        drawPath(roofPath, templeColor)
                    }
                }
                SlotSymbol.DIAMOND -> {
                    // Diamante cyan brillante
                    Canvas(modifier = Modifier.size(iconSize)) {
                        val diamondPath = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.05f)
                            lineTo(size.width * 0.92f, size.height * 0.4f)
                            lineTo(size.width * 0.5f, size.height * 0.95f)
                            lineTo(size.width * 0.08f, size.height * 0.4f)
                            close()
                        }
                        // Sombra
                        drawPath(
                            path = Path().apply {
                                moveTo(size.width * 0.52f, size.height * 0.08f)
                                lineTo(size.width * 0.94f, size.height * 0.42f)
                                lineTo(size.width * 0.52f, size.height * 0.97f)
                                lineTo(size.width * 0.1f, size.height * 0.42f)
                                close()
                            },
                            color = Color(0xFF006064)
                        )
                        // Diamante principal
                        drawPath(diamondPath, Color(0xFF4DD0E1))
                        // Brillo
                        val shinePath = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.1f)
                            lineTo(size.width * 0.35f, size.height * 0.4f)
                            lineTo(size.width * 0.5f, size.height * 0.35f)
                            close()
                        }
                        drawPath(shinePath, Color(0xFF80DEEA))
                    }
                }
            }
        }
    }
}

/**
 * Palanca de la máquina de slots con animación profesional
 * Diseño tipo casino clásico con bola brillante y estructura metálica
 */
@Composable
private fun SlotLever(
    onClick: () -> Unit,
    enabled: Boolean,
    isSpinning: Boolean
) {
    // Animación de la palanca cuando está girando (baja y sube)
    val leverRotation by animateFloatAsState(
        targetValue = if (isSpinning) 25f else 0f,
        animationSpec = tween(300),
        label = "lever_animation"
    )

    // Colores de la palanca
    val ballGradient = if (enabled) {
        listOf(
            Color(0xFFFF4444),  // Rojo brillante
            Color(0xFFCC0000),  // Rojo medio
            Color(0xFF880000)   // Rojo oscuro
        )
    } else {
        listOf(Color(0xFF666666), Color(0xFF444444), Color(0xFF333333))
    }
    
    val metalGradient = listOf(
        Color(0xFF8A8A8A),  // Metal claro
        Color(0xFF5A5A5A),  // Metal medio
        Color(0xFF3A3A3A),  // Metal oscuro
        Color(0xFF5A5A5A)   // Metal medio
    )

    Box(
        modifier = Modifier
            .width(50.dp)
            .height(160.dp)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.TopCenter
    ) {
        // Soporte/marco de la palanca (parte fija)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(30.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4A4A4A),
                            Color(0xFF2A2A2A),
                            Color(0xFF1A1A1A)
                        )
                    )
                )
                .border(1.dp, Color(0xFF5A5A5A), RoundedCornerShape(8.dp))
        )

        // Columna principal de la palanca (parte móvil)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset(y = leverRotation.dp)
        ) {
            // Bola de la palanca (con efecto 3D brillante)
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                // Sombra de la bola
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .offset(x = 2.dp, y = 2.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                
                // Bola principal con gradiente
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = ballGradient,
                                center = Offset(0.3f, 0.3f)
                            )
                        )
                )
                
                // Brillo de la bola (efecto cristal)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .offset(x = (-6).dp, y = (-6).dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.7f),
                                    Color.White.copy(alpha = 0f)
                                )
                            )
                        )
                )
            }

            // Barra de la palanca (cromada)
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = metalGradient
                        )
                    )
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFFAAAAAA),
                        shape = RoundedCornerShape(6.dp)
                    )
            )

            // Conector inferior (donde se une al soporte)
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6A6A6A),
                                Color(0xFF4A4A4A)
                            )
                        )
                    )
            )
        }
    }
}

/**
 * Sección de apuesta con chips, control de cantidad y auto-roll
 * Diseño premium con fondo diferenciado y bordes estilizados
 */
@Composable
private fun BetSection(
    selectedChip: Int,
    onChipSelected: (Int) -> Unit,
    currentBet: Int,
    betText: String,
    onBetTextChange: (String) -> Unit,
    onBetIncrease: () -> Unit,
    onBetDecrease: () -> Unit,
    selectedMultiplier: Int,
    onMultiplierSelected: (Int) -> Unit,
    onAutoRollClick: () -> Unit,
    isAutoRollActive: Boolean,
    enabled: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título "TU APUESTA" - estilo etiqueta dorada
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB8860B),
                            AccentGold,
                            Color(0xFFB8860B)
                        )
                    )
                )
                .padding(horizontal = 28.dp, vertical = 10.dp)
        ) {
            Text(
                text = "TU APUESTA",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        // Contenedor principal de apuesta con fondo oscuro y bordes redondeados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF252525),
                            Color(0xFF1A1A1A),
                            Color(0xFF252525)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFF3A3A3A),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fila de chips de apuesta rápida
            BetChipsRow(
                chips = listOf(1, 5, 10, 25, 100),
                selectedChip = selectedChip,
                onChipSelected = onChipSelected,
                enabled = enabled
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Control de cantidad de apuesta (editable)
            BetAmountControl(
                currentBet = currentBet,
                betText = betText,
                onBetTextChange = onBetTextChange,
                onIncrease = onBetIncrease,
                onDecrease = onBetDecrease,
                enabled = enabled
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila de Auto-Roll
            AutoRollRow(
                multipliers = listOf(3, 5, 10),
                selectedMultiplier = selectedMultiplier,
                onMultiplierSelected = onMultiplierSelected,
                onAutoRollClick = onAutoRollClick,
                isAutoRollActive = isAutoRollActive,
                enabled = enabled
            )
        }
    }
}

/**
 * Fila de chips de apuesta rápida (+1, +5, +10, etc)
 * Diseño con chips más estilizados y espaciado uniforme
 */
@Composable
private fun BetChipsRow(
    chips: List<Int>,
    selectedChip: Int,
    onChipSelected: (Int) -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chips.forEach { chip ->
            BetChip(
                value = chip,
                isSelected = chip == selectedChip,
                onClick = { onChipSelected(chip) },
                enabled = enabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Chip individual de apuesta - diseño premium
 */
@Composable
private fun BetChip(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFB8860B),
                AccentGold,
                Color(0xFFB8860B)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF1A1A1A),
                Color(0xFF1A1A1A)
            )
        )
    }
    
    val textColor = if (isSelected) Color.Black else if (enabled) Color.White else Color.Gray
    val borderColor = if (isSelected) AccentGold else Color(0xFF4A4A4A)

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+$value",
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Control de cantidad de apuesta con botones - y +
 * Diseño con botones cuadrados y campo central destacado
 */
@Composable
private fun BetAmountControl(
    currentBet: Int,
    betText: String,
    onBetTextChange: (String) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón decrementar - cuadrado con borde dorado
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1A1A1A))
                .border(
                    width = 2.dp,
                    color = if (enabled) AccentGold else Color(0xFF4A4A4A),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(enabled = enabled) { onDecrease() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "−",
                color = if (enabled) AccentGold else Color.Gray,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Campo editable para la cantidad - más prominente
        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0D0D0D))
                .border(1.dp, Color(0xFF3A3A3A), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = betText,
                onValueChange = { newValue ->
                    val filtered = newValue.filter { it.isDigit() }
                    onBetTextChange(filtered)
                },
                textStyle = TextStyle(
                    color = if (enabled) Color.White else Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                        Text(
                            text = " €",
                            color = if (enabled) AccentGold else Color.Gray,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }

        // Botón incrementar - cuadrado con borde dorado
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1A1A1A))
                .border(
                    width = 2.dp,
                    color = if (enabled) AccentGold else Color(0xFF4A4A4A),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(enabled = enabled) { onIncrease() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                color = if (enabled) AccentGold else Color.Gray,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Fila de Auto-Roll con botón y multiplicadores
 * Diseño premium con botones estilizados
 */
@Composable
private fun AutoRollRow(
    multipliers: List<Int>,
    selectedMultiplier: Int,
    onMultiplierSelected: (Int) -> Unit,
    onAutoRollClick: () -> Unit,
    isAutoRollActive: Boolean,
    enabled: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón AUTO-ROLL
        Box(
            modifier = Modifier
                .height(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isAutoRollActive) {
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFFB71C1C), Color(0xFFE53935), Color(0xFFB71C1C))
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF1A1A1A), Color(0xFF1A1A1A))
                        )
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (isAutoRollActive) Color(0xFFE53935) else Color(0xFF4A4A4A),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(enabled = enabled || isAutoRollActive) { onAutoRollClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isAutoRollActive) "PARAR" else "AUTO-ROLL",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Multiplicadores
        multipliers.forEach { mult ->
            MultiplierChip(
                multiplier = mult,
                isSelected = mult == selectedMultiplier,
                onClick = { onMultiplierSelected(mult) },
                enabled = enabled && !isAutoRollActive,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Chip de multiplicador para auto-roll - diseño premium
 */
@Composable
private fun MultiplierChip(
    multiplier: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        Brush.horizontalGradient(
            colors = listOf(
                Color(0xFFB8860B),
                AccentGold,
                Color(0xFFB8860B)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(Color(0xFF1A1A1A), Color(0xFF1A1A1A))
        )
    }
    
    val textColor = if (isSelected) Color.Black else if (enabled) Color.White else Color.Gray
    val borderColor = if (isSelected) AccentGold else Color(0xFF4A4A4A)

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "x$multiplier",
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// =====================================================================================
// DIÁLOGO DE AYUDA - REGLAS Y TABLA DE PAGOS
// =====================================================================================

/**
 * Diálogo de ayuda que muestra las reglas del juego y la tabla de pagos
 */
@Composable
private fun HelpDialog(
    slotName: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1E1E1E))
                .border(2.dp, AccentGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // ---------------------------------------------------------------
                // HEADER DEL DIÁLOGO
                // ---------------------------------------------------------------
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "AYUDA",
                        color = AccentGold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    
                    // Botón cerrar
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ---------------------------------------------------------------
                // CONTENIDO SCROLLABLE
                // ---------------------------------------------------------------
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // =====================================================
                    // SECCIÓN: CÓMO JUGAR
                    // =====================================================
                    HelpSectionTitle(title = "COMO JUGAR")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    HelpText("1. Selecciona tu apuesta usando los chips (+1, +5, +10, +25, +100) o escribe el importe manualmente.")
                    HelpText("2. Tira de la palanca roja para girar los rodillos.")
                    HelpText("3. Gana si consigues 3, 4 o 5 simbolos iguales consecutivos en una fila horizontal.")
                    HelpText("4. Las ganancias se calculan multiplicando tu apuesta por el multiplicador del simbolo.")

                    Spacer(modifier = Modifier.height(16.dp))

                    // =====================================================
                    // SECCIÓN: SÍMBOLOS Y PAGOS
                    // =====================================================
                    HelpSectionTitle(title = "TABLA DE PAGOS")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Encabezado de la tabla
                    PayTableHeader()
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Filas de la tabla de pagos
                    PayTableRow(
                        symbolEmoji = "⭐",
                        symbolName = "Estrella",
                        symbolColor = SymbolYellow,
                        x3 = "x2",
                        x4 = "x5",
                        x5 = "x10"
                    )
                    PayTableRow(
                        symbolEmoji = "♥",
                        symbolName = "Corazon",
                        symbolColor = SymbolRed,
                        x3 = "x3",
                        x4 = "x8",
                        x5 = "x15"
                    )
                    PayTableRow(
                        symbolEmoji = "⚡",
                        symbolName = "Rayo",
                        symbolColor = SymbolBlue,
                        x3 = "x5",
                        x4 = "x12",
                        x5 = "x25"
                    )
                    PayTableRow(
                        symbolEmoji = "🏛",
                        symbolName = "Templo",
                        symbolColor = SymbolWhite,
                        x3 = "x10",
                        x4 = "x25",
                        x5 = "x50"
                    )
                    PayTableRow(
                        symbolEmoji = "💎",
                        symbolName = "Diamante",
                        symbolColor = SymbolCyan,
                        x3 = "x15",
                        x4 = "x40",
                        x5 = "x100"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // =====================================================
                    // SECCIÓN: AUTO-ROLL
                    // =====================================================
                    HelpSectionTitle(title = "AUTO-ROLL")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    HelpText("Selecciona un multiplicador (x3, x5, x10) y pulsa AUTO-ROLL para realizar tiradas automaticas.")
                    HelpText("El auto-roll se detendra si te quedas sin saldo o si pulsas PARAR.")

                    Spacer(modifier = Modifier.height(16.dp))

                    // =====================================================
                    // SECCIÓN: REGLAS IMPORTANTES
                    // =====================================================
                    HelpSectionTitle(title = "REGLAS")
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    HelpText("• La apuesta minima es de 1€.")
                    HelpText("• No puedes apostar mas de tu saldo disponible.")
                    HelpText("• Los simbolos deben ser consecutivos desde la izquierda.")
                    HelpText("• Cada fila es una linea de pago independiente.")
                    HelpText("• Puedes ganar en multiples filas a la vez.")
                }
            }
        }
    }
}

/**
 * Título de sección en el diálogo de ayuda
 */
@Composable
private fun HelpSectionTitle(title: String) {
    Text(
        text = title,
        color = AccentGold,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Texto de ayuda con estilo consistente
 */
@Composable
private fun HelpText(text: String) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.9f),
        fontSize = 13.sp,
        lineHeight = 18.sp,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

/**
 * Encabezado de la tabla de pagos
 */
@Composable
private fun PayTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A2A2A), RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Simbolo",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = "x3",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "x4",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "x5",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Fila de la tabla de pagos con símbolo y multiplicadores
 */
@Composable
private fun PayTableRow(
    symbolEmoji: String,
    symbolName: String,
    symbolColor: Color,
    x3: String,
    x4: String,
    x5: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Símbolo y nombre
        Row(
            modifier = Modifier.weight(1.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = symbolEmoji,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = symbolName,
                color = symbolColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // Multiplicadores
        Text(
            text = x3,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = x4,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = x5,
            color = AccentGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}

// =====================================================================================
// PREVIEW
// =====================================================================================
@Preview(showBackground = true)
@Composable
fun SlotGamePlayScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        // Preview simplificado - en la app real se pasaría el ViewModel
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Preview no disponible\n(requiere ViewModels)",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
