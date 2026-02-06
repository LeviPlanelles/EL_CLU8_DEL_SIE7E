package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedCenter
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedEnd
import com.example.el_clu8_del_sie7e.ui.theme.ButtonRedStart
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import com.example.el_clu8_del_sie7e.viewmodel.RouletteBetType
import com.example.el_clu8_del_sie7e.viewmodel.RouletteState
import com.example.el_clu8_del_sie7e.viewmodel.RouletteViewModel
import com.example.el_clu8_del_sie7e.viewmodel.RouletteViewModelFactory

/**
 * =====================================================================================
 * ROULETTEGAMESCREEN.KT - PANTALLA DE RULETA DE CASINO (FUNCIONAL)
 * =====================================================================================
 *
 * Pantalla completa de Ruleta Europea conectada al RouletteViewModel.
 *
 * FUNCIONALIDADES:
 * - Tablero interactivo: tap en cualquier número/opción coloca una ficha
 * - Fichas visibles sobre las casillas donde se apostó
 * - Selector de fichas: $1, $10, $100, $200
 * - Historial dinámico de últimos números ganadores
 * - Botones: DESHACER (undo), GIRAR (spin), REPETIR
 * - Estado SPINNING: muestra "¡NO VA MÁS!" con animación
 * - Estado RESULT: muestra número ganador, ganancias y botón NUEVA RONDA
 * - Apuestas externas: Rojo/Negro, Par/Impar, 1-18/19-36, Docenas, Columnas
 *
 * =====================================================================================
 */

// ===================================================================================
// COLORES DE LA MESA DE RULETA
// ===================================================================================
private val RouletteGreen = Color(0xFF1B5E20)
private val RouletteRed = Color(0xFFB71C1C)
private val RouletteBlack = Color(0xFF212121)
private val ChipSilver = Color(0xFFB0BEC5)
private val ChipBlue = Color(0xFF1565C0)
private val ChipGreen = Color(0xFF2E7D32)
private val ChipRedDark = Color(0xFFC62828)
private val ChipGold = Color(0xFFD4AF36)

// ===================================================================================
// PANTALLA PRINCIPAL
// ===================================================================================

@Composable
fun RouletteGameScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel = viewModel(),
    rouletteViewModel: RouletteViewModel = viewModel(
        factory = RouletteViewModelFactory(balanceViewModel)
    )
) {
    // ===============================================================================
    // OBSERVAR ESTADOS DEL VIEWMODEL
    // ===============================================================================
    val balance by balanceViewModel.formattedBalance.collectAsStateWithLifecycle()
    val gameState by rouletteViewModel.gameState.collectAsStateWithLifecycle()
    val selectedChip by rouletteViewModel.selectedChip.collectAsStateWithLifecycle()
    val totalBet by rouletteViewModel.totalBet.collectAsStateWithLifecycle()
    val winningNumber by rouletteViewModel.winningNumber.collectAsStateWithLifecycle()
    val numberHistory by rouletteViewModel.numberHistory.collectAsStateWithLifecycle()
    val message by rouletteViewModel.message.collectAsStateWithLifecycle()
    val lastWin by rouletteViewModel.lastWin.collectAsStateWithLifecycle()
    val chipPositions by rouletteViewModel.chipPositions.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    // ===============================================================================
    // ESTRUCTURA PRINCIPAL
    // ===============================================================================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // -------------------------------------------------------------------------
            // HEADER CON LOGO Y BALANCE
            // -------------------------------------------------------------------------
            AppHeader(
                balance = balance,
                navController = navController
            )

            // -------------------------------------------------------------------------
            // CONTENIDO SCROLLEABLE
            // -------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                // SECCIÓN DE IMAGEN DE MESERA
                RouletteHeaderSection(
                    message = message,
                    numberHistory = numberHistory,
                    gameState = gameState,
                    winningNumber = winningNumber,
                    onBackClick = { navController.popBackStack() }
                )

                // RESULTADO GRANDE (cuando hay ganador)
                AnimatedVisibility(
                    visible = gameState == RouletteState.RESULT && winningNumber != null,
                    enter = fadeIn(tween(300)),
                    exit = fadeOut(tween(300))
                ) {
                    ResultBanner(
                        winningNumber = winningNumber ?: 0,
                        lastWin = lastWin,
                        isRed = rouletteViewModel.isRed(winningNumber ?: 0),
                        isBlack = rouletteViewModel.isBlack(winningNumber ?: 0)
                    )
                }

                // TABLA DE RULETA INTERACTIVA
                RouletteTableSection(
                    chipPositions = chipPositions,
                    gameState = gameState,
                    onNumberClick = { number -> rouletteViewModel.betOnNumber(number) },
                    onColumnClick = { column ->
                        val betType = when (column) {
                            1 -> RouletteBetType.COLUMN_1
                            2 -> RouletteBetType.COLUMN_2
                            else -> RouletteBetType.COLUMN_3
                        }
                        rouletteViewModel.betOnOption(betType)
                    }
                )

                // APUESTAS EXTERNAS (Docenas, Rojo/Negro, Par/Impar, etc.)
                ExternalBetsSection(
                    chipPositions = chipPositions,
                    gameState = gameState,
                    onBetOption = { betType -> rouletteViewModel.betOnOption(betType) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // -------------------------------------------------------------------------
            // CONTROLES STICKY (FICHAS + BOTONES)
            // -------------------------------------------------------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground)
                    .padding(vertical = 4.dp)
            ) {
                // INFO DE APUESTA ACTUAL
                if (totalBet > 0 || gameState == RouletteState.RESULT) {
                    BetInfoBar(
                        totalBet = totalBet,
                        lastWin = lastWin,
                        gameState = gameState
                    )
                }

                // FICHAS DE APUESTA
                BettingChipsSection(
                    selectedChip = selectedChip,
                    onChipSelected = { rouletteViewModel.selectChip(it) },
                    enabled = gameState == RouletteState.BETTING
                )

                // BOTONES DE ACCIÓN
                ActionButtonsSection(
                    gameState = gameState,
                    onUndo = { rouletteViewModel.undoLastBet() },
                    onSpin = { rouletteViewModel.spin() },
                    onRepeat = { rouletteViewModel.repeatLastBets() },
                    onNewRound = { rouletteViewModel.newRound() }
                )
            }

            // -------------------------------------------------------------------------
            // FOOTER DE NAVEGACIÓN
            // -------------------------------------------------------------------------
            AppFooter(
                selectedItem = "Mesas",
                onItemSelected = { },
                navController = navController
            )
        }
    }
}

// ===================================================================================
// HEADER CON MESERA
// ===================================================================================

/**
 * Header con imagen de mesera, mensaje dinámico del juego e historial de números.
 */
@Composable
private fun RouletteHeaderSection(
    message: String,
    numberHistory: List<Int>,
    gameState: RouletteState,
    winningNumber: Int?,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        // Imagen de fondo (mesera)
        Image(
            painter = painterResource(id = R.drawable.mesera),
            contentDescription = "Mesera de ruleta",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Overlay oscuro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Fila superior: Botón de regreso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón de regreso
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = AccentGold,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() }
                )

                // Indicador EN VIVO
                LiveIndicator()
            }

            Spacer(modifier = Modifier.weight(1f))

            // Título "LIVE ROULETTE"
            Text(
                text = "LIVE ROULETTE",
                color = AccentGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Mensaje dinámico del ViewModel (reemplaza "Hagan sus apuestas" estático)
            if (gameState == RouletteState.SPINNING) {
                // Texto pulsante durante el giro
                val infiniteTransition = rememberInfiniteTransition(label = "spin")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "alpha"
                )
                Text(
                    text = message,
                    color = Color.White.copy(alpha = alpha),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila inferior: Historial de números dinámico
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (numberHistory.isNotEmpty()) {
                    Text(
                        text = "ÚLTIMOS",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    // Mostrar hasta los últimos 6 números
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        reverseLayout = false
                    ) {
                        items(numberHistory.take(6)) { number ->
                            val color = when {
                                number == 0 -> RouletteGreen
                                number in RouletteViewModel.RED_NUMBERS -> RouletteRed
                                else -> RouletteBlack
                            }
                            LastNumberSquare(number = number, color = color)
                        }
                    }
                } else {
                    Text(
                        text = "Sin historial aún",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

/**
 * Indicador de "EN VIVO" con punto rojo pulsante.
 */
@Composable
private fun LiveIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(PrimaryRed, RoundedCornerShape(4.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "EN VIVO",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Cuadrado que muestra un número ganador reciente con su color correcto.
 */
@Composable
private fun LastNumberSquare(number: Int, color: Color) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .background(color, RoundedCornerShape(2.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ===================================================================================
// BANNER DE RESULTADO
// ===================================================================================

/**
 * Banner grande que muestra el número ganador y las ganancias.
 */
@Composable
private fun ResultBanner(
    winningNumber: Int,
    lastWin: Double,
    isRed: Boolean,
    isBlack: Boolean
) {
    val bgColor = when {
        winningNumber == 0 -> RouletteGreen
        isRed -> RouletteRed
        else -> RouletteBlack
    }
    val colorName = when {
        winningNumber == 0 -> "VERDE"
        isRed -> "ROJO"
        else -> "NEGRO"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        bgColor.copy(alpha = 0.3f),
                        bgColor.copy(alpha = 0.5f),
                        bgColor.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.dp, AccentGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Número grande
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(bgColor, CircleShape)
                .border(2.dp, AccentGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = winningNumber.toString(),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = colorName,
            color = AccentGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        if (lastWin > 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "¡GANASTE \$${lastWin.toInt()}!",
                color = AccentGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "MEJOR SUERTE LA PRÓXIMA",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

// ===================================================================================
// TABLA DE RULETA INTERACTIVA
// ===================================================================================

/**
 * Mesa de ruleta con números clicables. Al tocar un número se coloca la ficha
 * seleccionada. Muestra un indicador de ficha si ya hay apuesta en esa casilla.
 */
@Composable
private fun RouletteTableSection(
    chipPositions: Map<String, List<Int>>,
    gameState: RouletteState,
    onNumberClick: (Int) -> Unit,
    onColumnClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp)
    ) {
        // Número 0 (Verde) - Ancho completo
        val zeroKey = "straight_0"
        val zeroChips = chipPositions[zeroKey]
        val zeroTotal = zeroChips?.sum() ?: 0

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(RouletteGreen, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .border(
                    width = if (zeroTotal > 0) 2.dp else 1.dp,
                    color = if (zeroTotal > 0) AccentGold else AccentGold.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .clickable(enabled = gameState == RouletteState.BETTING) {
                    onNumberClick(0)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "0",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            // Mostrar ficha si hay apuesta
            if (zeroTotal > 0) {
                ChipOnCell(amount = zeroTotal)
            }
        }

        // Grid de números 1-36 (filas de 3)
        val rows = (1..36).chunked(3)
        rows.forEach { rowNumbers ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowNumbers.forEach { number ->
                    val isRed = number in RouletteViewModel.RED_NUMBERS
                    val backgroundColor = if (isRed) RouletteRed else RouletteBlack
                    val key = "straight_$number"
                    val chips = chipPositions[key]
                    val total = chips?.sum() ?: 0

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)
                            .background(backgroundColor)
                            .border(
                                width = if (total > 0) 2.dp else 1.dp,
                                color = if (total > 0) AccentGold else AccentGold.copy(alpha = 0.5f)
                            )
                            .clickable(enabled = gameState == RouletteState.BETTING) {
                                onNumberClick(number)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = number.toString(),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        // Ficha sobre la casilla
                        if (total > 0) {
                            ChipOnCell(amount = total)
                        }
                    }
                }
            }
        }

        // Fila de Columnas (2to1)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (col in 1..3) {
                val key = when (col) {
                    1 -> "column_1"
                    2 -> "column_2"
                    else -> "column_3"
                }
                val chips = chipPositions[key]
                val total = chips?.sum() ?: 0

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(DarkBackground)
                        .border(
                            width = if (total > 0) 2.dp else 1.dp,
                            color = if (total > 0) AccentGold else AccentGold.copy(alpha = 0.5f)
                        )
                        .clickable(enabled = gameState == RouletteState.BETTING) {
                            onColumnClick(col)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2to1",
                        color = AccentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (total > 0) {
                        ChipOnCell(amount = total)
                    }
                }
            }
        }
    }
}

// ===================================================================================
// APUESTAS EXTERNAS
// ===================================================================================

/**
 * Sección de apuestas externas: Docenas, Rojo/Negro, Par/Impar, 1-18/19-36.
 */
@Composable
private fun ExternalBetsSection(
    chipPositions: Map<String, List<Int>>,
    gameState: RouletteState,
    onBetOption: (RouletteBetType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Fila 1: Docenas
        Row(modifier = Modifier.fillMaxWidth()) {
            ExternalBetCell(
                label = "1ª DOC",
                subLabel = "1-12",
                key = "dozen_1",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.DOZEN_1) }
            )
            ExternalBetCell(
                label = "2ª DOC",
                subLabel = "13-24",
                key = "dozen_2",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.DOZEN_2) }
            )
            ExternalBetCell(
                label = "3ª DOC",
                subLabel = "25-36",
                key = "dozen_3",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.DOZEN_3) }
            )
        }

        // Fila 2: 1-18, PAR, ROJO, NEGRO, IMPAR, 19-36
        Row(modifier = Modifier.fillMaxWidth()) {
            ExternalBetCell(
                label = "1-18",
                key = "low",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.LOW) }
            )
            ExternalBetCell(
                label = "PAR",
                key = "even",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.EVEN) }
            )
            // ROJO - con color de fondo especial
            ExternalBetCell(
                label = "ROJO",
                key = "red",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                bgColor = RouletteRed.copy(alpha = 0.6f),
                onClick = { onBetOption(RouletteBetType.RED) }
            )
            // NEGRO - con color de fondo especial
            ExternalBetCell(
                label = "NEGRO",
                key = "black",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                bgColor = RouletteBlack,
                onClick = { onBetOption(RouletteBetType.BLACK) }
            )
            ExternalBetCell(
                label = "IMPAR",
                key = "odd",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.ODD) }
            )
            ExternalBetCell(
                label = "19-36",
                key = "high",
                chipPositions = chipPositions,
                enabled = gameState == RouletteState.BETTING,
                modifier = Modifier.weight(1f),
                onClick = { onBetOption(RouletteBetType.HIGH) }
            )
        }
    }
}

/**
 * Celda individual de apuesta externa.
 */
@Composable
private fun ExternalBetCell(
    label: String,
    key: String,
    chipPositions: Map<String, List<Int>>,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    subLabel: String? = null,
    bgColor: Color = DarkBackground,
    onClick: () -> Unit
) {
    val chips = chipPositions[key]
    val total = chips?.sum() ?: 0

    Box(
        modifier = modifier
            .height(40.dp)
            .background(bgColor)
            .border(
                width = if (total > 0) 2.dp else 1.dp,
                color = if (total > 0) AccentGold else AccentGold.copy(alpha = 0.5f)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                color = if (total > 0) AccentGold else Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subLabel != null) {
                Text(
                    text = subLabel,
                    color = Color.Gray,
                    fontSize = 8.sp
                )
            }
        }
        if (total > 0) {
            ChipOnCell(amount = total)
        }
    }
}

// ===================================================================================
// INDICADOR DE FICHA SOBRE CASILLA
// ===================================================================================

/**
 * Mini ficha visual que aparece sobre una casilla cuando tiene apuesta.
 * Se posiciona en la esquina superior derecha de la celda.
 */
@Composable
private fun ChipOnCell(amount: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .offset(x = (-2).dp, y = 2.dp)
                .size(20.dp)
                .background(ChipGold, CircleShape)
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (amount >= 1000) "${amount / 1000}K" else amount.toString(),
                color = Color.Black,
                fontSize = 7.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

// ===================================================================================
// BARRA DE INFO DE APUESTA
// ===================================================================================

/**
 * Barra que muestra la apuesta total y/o ganancias.
 */
@Composable
private fun BetInfoBar(
    totalBet: Double,
    lastWin: Double,
    gameState: RouletteState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(
                Color.White.copy(alpha = 0.05f),
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "APUESTA: \$${totalBet.toInt()}",
            color = AccentGold,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        if (gameState == RouletteState.RESULT && lastWin > 0) {
            Text(
                text = "GANANCIA: \$${lastWin.toInt()}",
                color = Color(0xFF4CAF50),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ===================================================================================
// FICHAS DE APUESTA
// ===================================================================================

/**
 * Selector de fichas: $1 (plata), $10 (azul), $100 (verde), $200 (rojo).
 * La ficha seleccionada tiene borde dorado destacado.
 */
@Composable
private fun BettingChipsSection(
    selectedChip: Int,
    onChipSelected: (Int) -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BettingChip(value = 1, color = ChipSilver, isSelected = selectedChip == 1, enabled = enabled) {
            onChipSelected(1)
        }
        BettingChip(value = 10, color = ChipBlue, isSelected = selectedChip == 10, enabled = enabled) {
            onChipSelected(10)
        }
        BettingChip(value = 100, color = ChipGreen, isSelected = selectedChip == 100, enabled = enabled) {
            onChipSelected(100)
        }
        BettingChip(value = 200, color = ChipRedDark, isSelected = selectedChip == 200, enabled = enabled) {
            onChipSelected(200)
        }
    }
}

/**
 * Ficha individual de apuesta con diseño de casino.
 * Borde dorado si está seleccionada, opacidad reducida si está deshabilitada.
 */
@Composable
private fun BettingChip(
    value: Int,
    color: Color,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val chipAlpha = if (enabled) 1f else 0.4f

    Box(
        modifier = Modifier
            .size(if (isSelected) 58.dp else 52.dp)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, AccentGold, CircleShape)
                } else Modifier
            )
            .clip(CircleShape)
            .background(color.copy(alpha = chipAlpha))
            .border(2.dp, Color.White.copy(alpha = 0.3f * chipAlpha), CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Borde decorativo interno
        Box(
            modifier = Modifier
                .size(if (isSelected) 46.dp else 40.dp)
                .border(1.dp, Color.White.copy(alpha = 0.5f * chipAlpha), CircleShape)
        )

        // Valor de la ficha (sin símbolo $ para que quepa)
        Text(
            text = value.toString(),
            color = Color.White.copy(alpha = chipAlpha),
            fontSize = if (value >= 100) 13.sp else 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ===================================================================================
// BOTONES DE ACCIÓN
// ===================================================================================

/**
 * Botones de acción que cambian según el estado del juego:
 * - BETTING: DESHACER | GIRAR | REPETIR
 * - SPINNING: Deshabilitados
 * - RESULT: NUEVA RONDA (botón único central)
 */
@Composable
private fun ActionButtonsSection(
    gameState: RouletteState,
    onUndo: () -> Unit,
    onSpin: () -> Unit,
    onRepeat: () -> Unit,
    onNewRound: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (gameState) {
            RouletteState.BETTING -> {
                // DESHACER
                ActionButton(
                    text = "DESHACER",
                    icon = Icons.AutoMirrored.Filled.Undo,
                    isPrimary = false,
                    onClick = onUndo
                )
                // GIRAR (botón principal)
                ActionButton(
                    text = "GIRAR",
                    icon = null,
                    isPrimary = true,
                    onClick = onSpin
                )
                // REPETIR
                ActionButton(
                    text = "REPETIR",
                    icon = Icons.AutoMirrored.Filled.Redo,
                    isPrimary = false,
                    onClick = onRepeat
                )
            }

            RouletteState.SPINNING -> {
                // Todo deshabilitado durante el giro
                ActionButton(
                    text = "DESHACER",
                    icon = Icons.AutoMirrored.Filled.Undo,
                    isPrimary = false,
                    enabled = false,
                    onClick = {}
                )
                ActionButton(
                    text = "¡NO VA MÁS!",
                    icon = null,
                    isPrimary = true,
                    enabled = false,
                    onClick = {}
                )
                ActionButton(
                    text = "REPETIR",
                    icon = Icons.AutoMirrored.Filled.Redo,
                    isPrimary = false,
                    enabled = false,
                    onClick = {}
                )
            }

            RouletteState.RESULT -> {
                // Un solo botón grande: NUEVA RONDA
                Spacer(modifier = Modifier.width(8.dp))
                ActionButton(
                    text = "NUEVA RONDA",
                    icon = null,
                    isPrimary = true,
                    isWide = true,
                    onClick = onNewRound
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

/**
 * Botón de acción individual con estilo casino.
 * - Primario: gradiente rojo con borde dorado
 * - Secundario: fondo oscuro con borde dorado
 */
@Composable
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    isPrimary: Boolean,
    enabled: Boolean = true,
    isWide: Boolean = false,
    onClick: () -> Unit
) {
    val buttonShape = RoundedCornerShape(22.dp)
    val borderColor = if (enabled) AccentGold.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.3f)
    val buttonWidth = if (isWide) 200.dp else 110.dp
    val contentAlpha = if (enabled) 1f else 0.4f

    if (isPrimary) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .height(44.dp)
                .border(width = 1.5.dp, color = borderColor, shape = buttonShape)
                .clip(buttonShape)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = if (enabled) {
                            listOf(ButtonRedStart, ButtonRedCenter, ButtonRedEnd)
                        } else {
                            listOf(Color.Gray.copy(alpha = 0.3f), Color.Gray.copy(alpha = 0.4f), Color.Gray.copy(alpha = 0.3f))
                        }
                    )
                )
                .clickable(enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White.copy(alpha = contentAlpha),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    } else {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .height(44.dp)
                .border(width = 1.5.dp, color = borderColor, shape = buttonShape)
                .clip(buttonShape)
                .background(Color(0xFF2E2E2E).copy(alpha = if (enabled) 1f else 0.5f))
                .clickable(enabled = enabled, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = contentAlpha),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = text,
                    color = Color.White.copy(alpha = contentAlpha),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
