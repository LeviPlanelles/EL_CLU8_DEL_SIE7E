package com.example.el_clu8_del_sie7e.ui.screens

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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel

/**
 * =====================================================================================
 * SLOTGAMEPLAYSCREEN.KT - PANTALLA DE JUEGO DE SLOT MACHINE
 * =====================================================================================
 *
 * Esta pantalla muestra el juego de slot machine interactivo.
 *
 * ESTRUCTURA:
 * -----------
 * - AppHeader: Logo y balance del usuario
 * - Título del juego con botón atrás y ayuda (?)
 * - Máquina de slots: 5 carriles x 3 filas con símbolos
 * - Palanca roja para girar
 * - Sección de apuesta: chips de apuesta rápida
 * - Control de cantidad: - / cantidad / +
 * - Auto-roll: multiplicadores de tiradas automáticas
 * - AppFooter: Navegación inferior
 *
 * NOTA: Esta pantalla es solo UI, la funcionalidad del juego
 * se implementará posteriormente.
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

// Colores de los símbolos
private val SymbolYellow = Color(0xFFFFD54F)        // Amarillo para estrella
private val SymbolRed = Color(0xFFE53935)           // Rojo para corazón
private val SymbolBlue = Color(0xFF42A5F5)          // Azul para rayo/diamante
private val SymbolCyan = Color(0xFF4DD0E1)          // Cyan para diamante
private val SymbolGray = Color(0xFF9E9E9E)          // Gris para 7

@Composable
fun SlotGamePlayScreen(
    navController: NavController,
    slotName: String,
    balanceViewModel: BalanceViewModel,
    modifier: Modifier = Modifier
) {
    // Estado del balance
    val formattedBalance by balanceViewModel.formattedBalance.collectAsState()
    
    // Estados de la UI (solo para mostrar, sin funcionalidad real)
    var selectedBetChip by remember { mutableIntStateOf(5) }    // Chip seleccionado (+1, +5, etc)
    var currentBet by remember { mutableIntStateOf(5) }         // Apuesta actual
    var selectedMultiplier by remember { mutableIntStateOf(0) } // 0 = ninguno, 3, 5, 10

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
                    onBackClick = { navController.popBackStack() },
                    onHelpClick = { /* TODO: Mostrar ayuda */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ---------------------------------------------------------------
                // 2.2 MÁQUINA DE SLOTS CON PALANCA
                // ---------------------------------------------------------------
                SlotMachineWithLever(
                    onLeverPull = { /* TODO: Implementar giro */ }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ---------------------------------------------------------------
                // 2.3 SECCIÓN "TU APUESTA"
                // ---------------------------------------------------------------
                BetSection(
                    selectedChip = selectedBetChip,
                    onChipSelected = { chip ->
                        selectedBetChip = chip
                        currentBet = chip
                    },
                    currentBet = currentBet,
                    onBetIncrease = { currentBet += selectedBetChip },
                    onBetDecrease = { if (currentBet > selectedBetChip) currentBet -= selectedBetChip },
                    selectedMultiplier = selectedMultiplier,
                    onMultiplierSelected = { mult ->
                        selectedMultiplier = if (selectedMultiplier == mult) 0 else mult
                    }
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
    onLeverPull: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Máquina de slots
        SlotMachine()

        Spacer(modifier = Modifier.width(8.dp))

        // Palanca
        SlotLever(onClick = onLeverPull)
    }
}

/**
 * Máquina de slots con 5 carriles y 3 filas
 */
@Composable
private fun SlotMachine() {
    // Símbolos de ejemplo para mostrar en la máquina (3 filas x 5 columnas)
    val symbols = listOf(
        listOf("7", "7", "7", "7", "7"),      // Fila superior (gris/desenfocada)
        listOf("star", "heart", "bolt", "temple", "diamond"), // Fila central (activa)
        listOf("7", "7", "7", "7", "7")       // Fila inferior (gris/desenfocada)
    )

    Box(
        modifier = Modifier
            .width(280.dp)
            .height(340.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SlotMachineBg)
            .border(2.dp, SlotBorderGold.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            symbols.forEachIndexed { rowIndex, row ->
                val isActiveRow = rowIndex == 1 // Fila central es la activa
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    row.forEach { symbol ->
                        SlotSymbol(
                            symbol = symbol,
                            isActive = isActiveRow
                        )
                    }
                }
                
                // Línea dorada después de la primera fila (línea de pago superior)
                if (rowIndex == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(AccentGold.copy(alpha = 0.5f))
                    )
                }
                
                // Línea dorada después de la fila activa (línea de pago inferior)
                if (rowIndex == 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(AccentGold.copy(alpha = 0.5f))
                    )
                }
            }
        }
    }
}

/**
 * Símbolo individual de la máquina de slots
 * Usa composables simples para representar cada símbolo
 */
@Composable
private fun SlotSymbol(
    symbol: String,
    isActive: Boolean
) {
    val alpha = if (isActive) 1f else 0.3f
    
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SlotReelBg),
        contentAlignment = Alignment.Center
    ) {
        when (symbol) {
            "7" -> {
                // Número 7 con Text
                Text(
                    text = "7",
                    color = SymbolGray.copy(alpha = alpha),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            "star" -> {
                // Estrella amarilla - círculo con estrella interior
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SymbolYellow.copy(alpha = alpha)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "★",
                        color = Color.Black.copy(alpha = alpha * 0.8f),
                        fontSize = 18.sp
                    )
                }
            }
            "heart" -> {
                // Corazón rojo - círculo con corazón
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SymbolRed.copy(alpha = alpha)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "♥",
                        color = Color.White.copy(alpha = alpha),
                        fontSize = 16.sp
                    )
                }
            }
            "bolt" -> {
                // Rayo azul
                Text(
                    text = "⚡",
                    color = SymbolBlue.copy(alpha = alpha),
                    fontSize = 24.sp
                )
            }
            "temple" -> {
                // Templo griego - usando Canvas para dibujar
                Canvas(modifier = Modifier.size(32.dp)) {
                    val templeColor = Color.White.copy(alpha = alpha * 0.9f)
                    // Base
                    drawRoundRect(
                        color = templeColor,
                        topLeft = Offset(size.width * 0.2f, size.height * 0.75f),
                        size = Size(size.width * 0.6f, size.height * 0.1f),
                        cornerRadius = CornerRadius(2f)
                    )
                    // Columnas
                    drawRoundRect(
                        color = templeColor,
                        topLeft = Offset(size.width * 0.25f, size.height * 0.35f),
                        size = Size(size.width * 0.1f, size.height * 0.4f),
                        cornerRadius = CornerRadius(2f)
                    )
                    drawRoundRect(
                        color = templeColor,
                        topLeft = Offset(size.width * 0.45f, size.height * 0.35f),
                        size = Size(size.width * 0.1f, size.height * 0.4f),
                        cornerRadius = CornerRadius(2f)
                    )
                    drawRoundRect(
                        color = templeColor,
                        topLeft = Offset(size.width * 0.65f, size.height * 0.35f),
                        size = Size(size.width * 0.1f, size.height * 0.4f),
                        cornerRadius = CornerRadius(2f)
                    )
                    // Techo triangular
                    val roofPath = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.15f)
                        lineTo(size.width * 0.15f, size.height * 0.35f)
                        lineTo(size.width * 0.85f, size.height * 0.35f)
                        close()
                    }
                    drawPath(roofPath, templeColor)
                }
            }
            "diamond" -> {
                // Diamante cyan
                Canvas(modifier = Modifier.size(32.dp)) {
                    val diamondPath = Path().apply {
                        moveTo(size.width * 0.5f, size.height * 0.1f)
                        lineTo(size.width * 0.85f, size.height * 0.4f)
                        lineTo(size.width * 0.5f, size.height * 0.9f)
                        lineTo(size.width * 0.15f, size.height * 0.4f)
                        close()
                    }
                    drawPath(diamondPath, SymbolCyan.copy(alpha = alpha))
                }
            }
        }
    }
}

/**
 * Palanca de la máquina de slots
 */
@Composable
private fun SlotLever(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        // Bola roja de la palanca
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            LeverRed,
                            LeverRedDark
                        )
                    )
                )
        )

        // Palo de la palanca
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF3A3A3A),
                            Color(0xFF5A5A5A),
                            Color(0xFF3A3A3A)
                        )
                    )
                )
        )

        // Base de la palanca
        Box(
            modifier = Modifier
                .width(16.dp)
                .height(12.dp)
                .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                .background(Color(0xFF4A4A4A))
        )
    }
}

/**
 * Sección de apuesta con chips, control de cantidad y auto-roll
 */
@Composable
private fun BetSection(
    selectedChip: Int,
    onChipSelected: (Int) -> Unit,
    currentBet: Int,
    onBetIncrease: () -> Unit,
    onBetDecrease: () -> Unit,
    selectedMultiplier: Int,
    onMultiplierSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título "TU APUESTA"
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(AccentGold)
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            Text(
                text = "TU APUESTA",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Contenedor principal de apuesta
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(BetControlBg)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fila de chips de apuesta rápida
            BetChipsRow(
                chips = listOf(1, 5, 10, 25, 100),
                selectedChip = selectedChip,
                onChipSelected = onChipSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Control de cantidad de apuesta
            BetAmountControl(
                currentBet = currentBet,
                onIncrease = onBetIncrease,
                onDecrease = onBetDecrease
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila de Auto-Roll
            AutoRollRow(
                multipliers = listOf(3, 5, 10),
                selectedMultiplier = selectedMultiplier,
                onMultiplierSelected = onMultiplierSelected
            )
        }
    }
}

/**
 * Fila de chips de apuesta rápida (+1, +5, +10, etc)
 */
@Composable
private fun BetChipsRow(
    chips: List<Int>,
    selectedChip: Int,
    onChipSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        chips.forEach { chip ->
            BetChip(
                value = chip,
                isSelected = chip == selectedChip,
                onClick = { onChipSelected(chip) }
            )
        }
    }
}

/**
 * Chip individual de apuesta
 */
@Composable
private fun BetChip(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) ChipSelectedBg else ChipUnselectedBg
    val textColor = if (isSelected) Color.Black else Color.White
    val borderColor = if (isSelected) ChipSelectedBg else ChipBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
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
 */
@Composable
private fun BetAmountControl(
    currentBet: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón decrementar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, AccentGold, RoundedCornerShape(8.dp))
                .clickable { onDecrease() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Disminuir apuesta",
                tint = AccentGold,
                modifier = Modifier.size(24.dp)
            )
        }

        // Cantidad actual
        Box(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1A1A1A))
                .border(1.dp, Color(0xFF3A3A3A), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+$currentBet €",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Botón incrementar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, AccentGold, RoundedCornerShape(8.dp))
                .clickable { onIncrease() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar apuesta",
                tint = AccentGold,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Fila de Auto-Roll con botón y multiplicadores
 */
@Composable
private fun AutoRollRow(
    multipliers: List<Int>,
    selectedMultiplier: Int,
    onMultiplierSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón AUTO-ROLL
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1A1A1A))
                .border(1.dp, Color(0xFF3A3A3A), RoundedCornerShape(8.dp))
                .clickable { /* TODO: Activar auto-roll */ }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AUTO-ROLL",
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
                onClick = { onMultiplierSelected(mult) }
            )
        }
    }
}

/**
 * Chip de multiplicador para auto-roll
 */
@Composable
private fun MultiplierChip(
    multiplier: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) AccentGold else Color(0xFF1A1A1A)
    val textColor = if (isSelected) Color.Black else Color.White
    val borderColor = if (isSelected) AccentGold else Color(0xFF3A3A3A)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
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
// PREVIEW
// =====================================================================================
@Preview(showBackground = true)
@Composable
fun SlotGamePlayScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        SlotGamePlayScreen(
            navController = rememberNavController(),
            slotName = "Zeus Slot",
            balanceViewModel = BalanceViewModel()
        )
    }
}
