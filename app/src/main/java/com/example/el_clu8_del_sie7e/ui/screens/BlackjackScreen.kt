package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.el_clu8_del_sie7e.ui.components.AppFooter
import com.example.el_clu8_del_sie7e.ui.components.AppHeader
import com.example.el_clu8_del_sie7e.ui.components.blackjack.*
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.GradientCenter
import com.example.el_clu8_del_sie7e.ui.theme.GradientEdge
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import com.example.el_clu8_del_sie7e.viewmodel.BlackjackState
import com.example.el_clu8_del_sie7e.viewmodel.BlackjackViewModel
import com.example.el_clu8_del_sie7e.viewmodel.BlackjackViewModelFactory
import com.example.el_clu8_del_sie7e.viewmodel.GameResult

/**
 * =====================================================================================
 * BLACKJACKSCREEN.KT - PANTALLA DEL JUEGO DE BLACKJACK
 * =====================================================================================
 *
 * Pantalla principal del juego de Blackjack, conectada con BlackjackViewModel
 * para manejar toda la lógica del juego.
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - Interfaz completa de Blackjack con cartas animadas
 * - Sistema de apuestas con fichas de diferentes valores
 * - Acciones: Pedir (Hit), Plantarse (Stand), Doblar, Dividir
 * - Integración con el balance del usuario
 * - Feedback visual del estado del juego
 * - Usa AppHeader y AppFooter como las demás pantallas
 *
 * =====================================================================================
 */

@Composable
fun BlackjackScreen(
    navController: NavController,
    balanceViewModel: BalanceViewModel = viewModel()
) {
    // ===============================================================================
    // VIEWMODEL
    // ===============================================================================
    
    val blackjackViewModel: BlackjackViewModel = viewModel(
        factory = BlackjackViewModelFactory(balanceViewModel)
    )

    // ===============================================================================
    // ESTADOS DEL VIEWMODEL
    // ===============================================================================
    
    val gameState by blackjackViewModel.gameState.collectAsStateWithLifecycle()
    val currentBet by blackjackViewModel.currentBet.collectAsStateWithLifecycle()
    val selectedChip by blackjackViewModel.selectedChip.collectAsStateWithLifecycle()
    val playerCards by blackjackViewModel.playerCards.collectAsStateWithLifecycle()
    val dealerCards by blackjackViewModel.dealerCards.collectAsStateWithLifecycle()
    val playerScore by blackjackViewModel.playerScore.collectAsStateWithLifecycle()
    val dealerScore by blackjackViewModel.dealerScore.collectAsStateWithLifecycle()
    val gameResult by blackjackViewModel.gameResult.collectAsStateWithLifecycle()
    val message by blackjackViewModel.message.collectAsStateWithLifecycle()
    val canDouble by blackjackViewModel.canDouble.collectAsStateWithLifecycle()
    val canSplit by blackjackViewModel.canSplit.collectAsStateWithLifecycle()
    val balance by balanceViewModel.formattedBalance.collectAsStateWithLifecycle()

    // Estado de navegación del footer
    var selectedFooterItem by remember { mutableStateOf("Mesas") }
    
    // Variable para guardar la última apuesta (para repetir)
    var lastBet by remember { mutableStateOf(0.0) }
    
    // Actualizar última apuesta cuando termine el juego
    LaunchedEffect(gameState) {
        if (blackjackViewModel.isGameOver() && currentBet > 0) {
            lastBet = currentBet
        }
    }

    // ===============================================================================
    // UI PRINCIPAL
    // ===============================================================================
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ============================================
            // HEADER - Logo y Balance (componente reutilizable)
            // ============================================
            AppHeader(
                balance = balance,
                navController = navController
            )
            
            // ============================================
            // TITULO BLACKJACK
            // ============================================
            BlackjackTitleBar(
                onBackClick = { navController.popBackStack() }
            )
            
            // ============================================
            // AREA DE JUEGO (dentro de un contenedor)
            // ============================================
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                // Contenedor del juego con bordes redondeados y fondo degradado rojo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(GradientCenter, GradientEdge),
                                radius = 900f
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // ----- DEALER SECTION -----
                        DealerSection(
                            cards = dealerCards,
                            score = dealerScore,
                            currentBet = currentBet,
                            showScore = gameState != BlackjackState.BETTING && dealerCards.isNotEmpty()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // ----- RESULTADO/MENSAJE -----
                        if (message.isNotEmpty()) {
                            ResultBanner(
                                text = message,
                                isWin = gameResult in listOf(
                                    GameResult.PLAYER_WINS,
                                    GameResult.PLAYER_BLACKJACK,
                                    GameResult.DEALER_BUST
                                ),
                                isLoss = gameResult in listOf(
                                    GameResult.DEALER_WINS,
                                    GameResult.PLAYER_BUST
                                )
                            )
                        } else if (gameState == BlackjackState.BETTING) {
                            ResultBanner(text = "COLOCA TU APUESTA", isNeutral = true)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // ----- PLAYER SECTION -----
                        PlayerSection(
                            cards = playerCards,
                            score = playerScore,
                            showScore = playerCards.isNotEmpty()
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // ----- CHIPS SELECTOR (fuera del contenedor) -----
                if (gameState == BlackjackState.BETTING) {
                    ChipSelectorRow(
                        selectedChip = selectedChip,
                        onChipSelected = { blackjackViewModel.selectChip(it) },
                        onChipClicked = { blackjackViewModel.addBetAmount(it) }
                    )
                } else {
                    // Mostrar indicador de turno
                    TurnIndicator(gameState = gameState)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // ----- BOTONES DE ACCION -----
                ActionButtons(
                    gameState = gameState,
                    canDouble = canDouble,
                    canSplit = canSplit,
                    currentBet = currentBet,
                    lastBet = lastBet,
                    onDeal = { blackjackViewModel.deal() },
                    onHit = { blackjackViewModel.hit() },
                    onStand = { blackjackViewModel.stand() },
                    onDouble = { blackjackViewModel.doubleDown() },
                    onSplit = { blackjackViewModel.split() },
                    onNewRound = { blackjackViewModel.newRound() },
                    onRepeatBet = { blackjackViewModel.repeatBet(lastBet) },
                    onClearBet = { blackjackViewModel.clearBet() }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // ============================================
            // FOOTER - Navegación (componente reutilizable)
            // ============================================
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = navController
            )
        }
    }
}

/**
 * Barra de título BLACKJACK - Sin fondo, solo texto dorado con línea divisora
 */
@Composable
private fun BlackjackTitleBar(onBackClick: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Botón atrás (izquierda)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            
            // Título centrado
            Text(
                text = "BLACKJACK",
                color = AccentGold,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 2.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            
            // Icono de ayuda (derecha)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .clickable { /* TODO: Mostrar ayuda */ },
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
        
        // Línea divisora con degradado - más visible en el centro, se desvanece en los lados
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
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
}

/**
 * Sección del Dealer con cartas
 */
@Composable
private fun DealerSection(
    cards: List<Card>,
    score: Int,
    currentBet: Double,
    showScore: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Apuesta actual (izquierda) - Mejorado visualmente
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = "APUESTA",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF1A1A1A).copy(alpha = 0.8f),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (currentBet > 0) AccentGold.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.3f)
                )
            ) {
                Text(
                    text = "$${currentBet.toInt()}",
                    color = if (currentBet > 0) AccentGold else Color.White.copy(alpha = 0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
        
        // Cartas y score del dealer (centro)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Label DEALER con score
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "DEALER",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
                if (showScore && score > 0) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF2A2A2A)
                    ) {
                        Text(
                            text = score.toString(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Cartas del dealer o placeholder
            if (cards.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy((-20).dp)) {
                    cards.forEachIndexed { index, card ->
                        val rotation = when {
                            cards.size == 1 -> 0f
                            index == 0 -> -8f
                            index == cards.lastIndex -> 8f
                            else -> 0f
                        }
                        PlayingCard(
                            card = card,
                            width = 65.dp,
                            rotation = rotation
                        )
                    }
                }
            } else {
                // Placeholder para cartas
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(90.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🃏",
                        fontSize = 36.sp,
                        color = Color.White.copy(alpha = 0.2f)
                    )
                }
            }
        }
        
        // Logo del casino (derecha)
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF8B0000), Color(0xFF4A0000))
                    )
                )
                .border(2.dp, AccentGold.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "♠", fontSize = 24.sp, color = AccentGold)
        }
    }
}

/**
 * Banner de resultado con colores según el resultado
 */
@Composable
private fun ResultBanner(
    text: String,
    isWin: Boolean = false,
    isLoss: Boolean = false,
    isNeutral: Boolean = false
) {
    val backgroundColor = when {
        isWin -> Color(0xFF1B5E20).copy(alpha = 0.8f)  // Verde oscuro
        isLoss -> Color(0xFF8B0000).copy(alpha = 0.8f) // Rojo oscuro
        else -> Color(0xFF2D2D2D)
    }
    
    val textColor = when {
        isWin -> Color(0xFF4CAF50)  // Verde
        isLoss -> Color(0xFFEF5350) // Rojo
        isNeutral -> Color.White.copy(alpha = 0.7f)
        else -> AccentGold
    }
    
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
        )
    }
}

/**
 * Sección del jugador con cartas
 */
@Composable
private fun PlayerSection(
    cards: List<Card>,
    score: Int,
    showScore: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cartas del jugador o placeholder
        if (cards.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy((-20).dp)) {
                cards.forEachIndexed { index, card ->
                    val rotation = when {
                        cards.size == 1 -> 0f
                        index == 0 -> -8f
                        index == cards.lastIndex -> 8f
                        else -> ((index - cards.size / 2) * 4).toFloat()
                    }
                    PlayingCard(
                        card = card,
                        width = 65.dp,
                        rotation = rotation
                    )
                }
            }
        } else {
            // Placeholder para cartas
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = 36.sp,
                    color = Color.White.copy(alpha = 0.2f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Label JUGADOR con score
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "JUGADOR",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp
            )
            if (showScore && score > 0) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when {
                        score > 21 -> Color(0xFF8B0000) // Rojo si se pasó
                        score == 21 -> Color(0xFF1B5E20) // Verde si es 21
                        else -> Color(0xFF2A2A2A)
                    }
                ) {
                    Text(
                        text = score.toString(),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Indicador de turno durante el juego
 */
@Composable
private fun TurnIndicator(gameState: BlackjackState) {
    val text = when (gameState) {
        BlackjackState.DEALING -> "REPARTIENDO..."
        BlackjackState.PLAYER_TURN -> "TU TURNO"
        BlackjackState.DEALER_TURN -> "TURNO DEL DEALER"
        else -> ""
    }
    
    if (text.isNotEmpty()) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF3A3A3A)
        ) {
            Text(
                text = text,
                color = when (gameState) {
                    BlackjackState.PLAYER_TURN -> AccentGold
                    BlackjackState.DEALER_TURN -> Color(0xFFEF5350)
                    else -> Color.White
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }
    }
}

/**
 * Fila de selección de fichas para apostar
 */
@Composable
private fun ChipSelectorRow(
    selectedChip: Int,
    onChipSelected: (Int) -> Unit,
    onChipClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Fichas disponibles con valores más legibles
        val chips = listOf(
            1 to ChipColor.BLACK,
            5 to ChipColor.RED,
            10 to ChipColor.BLUE,
            25 to ChipColor.GREEN,
            100 to ChipColor.RED,
            500 to ChipColor.GOLD
        )
        
        chips.forEach { (value, color) ->
            val isSelected = selectedChip == value
            // Formato del valor: sin $ para que quepa mejor
            val displayValue = when (value) {
                1 -> "1"
                5 -> "5"
                10 -> "10"
                25 -> "25"
                100 -> "100"
                500 -> "500"
                else -> value.toString()
            }
            
            Box(
                modifier = Modifier
                    .clickable { 
                        onChipSelected(value)
                        onChipClicked(value)
                    }
            ) {
                PokerChip(
                    value = displayValue,
                    chipColor = color,
                    size = if (isSelected) 52.dp else 44.dp
                )
                // Indicador de selección
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(AccentGold)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            }
        }
    }
}

/**
 * Botones de acción del juego - cambian según el estado
 */
@Composable
private fun ActionButtons(
    gameState: BlackjackState,
    canDouble: Boolean,
    canSplit: Boolean,
    currentBet: Double,
    lastBet: Double,
    onDeal: () -> Unit,
    onHit: () -> Unit,
    onStand: () -> Unit,
    onDouble: () -> Unit,
    onSplit: () -> Unit,
    onNewRound: () -> Unit,
    onRepeatBet: () -> Unit,
    onClearBet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (gameState) {
            // FASE DE APUESTAS
            BlackjackState.BETTING -> {
                // Fila 1: LIMPIAR y REPARTIR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // LIMPIAR APUESTA
                    OutlinedButton(
                        onClick = onClearBet,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray.copy(alpha = 0.4f)),
                        enabled = currentBet > 0
                    ) {
                        Text(
                            text = "LIMPIAR",
                            color = if (currentBet > 0) Color.White else Color.Gray,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    
                    // REPARTIR
                    Button(
                        onClick = onDeal,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentBet > 0) Color(0xFF8B0000) else Color(0xFF3A3A3A)
                        ),
                        enabled = currentBet > 0
                    ) {
                        Text(
                            text = "REPARTIR",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
                
                // Fila 2: REPETIR APUESTA (si hay apuesta anterior)
                if (lastBet > 0) {
                    OutlinedButton(
                        onClick = onRepeatBet,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentGold.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "REPETIR APUESTA (\$${String.format("%.0f", lastBet)})",
                            color = AccentGold,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            
            // TURNO DEL JUGADOR
            BlackjackState.PLAYER_TURN -> {
                // Fila 1: PLANTARSE y PEDIR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // PLANTARSE (Stand)
                    Button(
                        onClick = onStand,
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A3A3A))
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "PLANTARSE",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Stand",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                        }
                    }
                    
                    // PEDIR (Hit)
                    Button(
                        onClick = onHit,
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "PEDIR",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Hit",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
                
                // Fila 2: DOBLAR y DIVIDIR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // DOBLAR
                    OutlinedButton(
                        onClick = onDouble,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            if (canDouble) AccentGold.copy(alpha = 0.6f) else Color.Gray.copy(alpha = 0.3f)
                        ),
                        enabled = canDouble
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "💰", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "DOBLAR",
                                color = if (canDouble) AccentGold else Color.Gray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                    
                    // DIVIDIR
                    OutlinedButton(
                        onClick = onSplit,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, 
                            if (canSplit) AccentGold.copy(alpha = 0.6f) else Color.Gray.copy(alpha = 0.3f)
                        ),
                        enabled = canSplit
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "✌️", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "DIVIDIR",
                                color = if (canSplit) AccentGold else Color.Gray,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
            
            // TURNO DEL DEALER o REPARTIENDO
            BlackjackState.DEALING, BlackjackState.DEALER_TURN -> {
                // Botones deshabilitados durante el turno del dealer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                            .height(58.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A2A2A)),
                        enabled = false
                    ) {
                        Text(
                            text = "ESPERA...",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
            
            // JUEGO TERMINADO
            else -> {
                // Botón NUEVA MANO
                Button(
                    onClick = onNewRound,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                ) {
                    Text(
                        text = "NUEVA MANO",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

// ===================================================================================
// PREVIEWS
// ===================================================================================

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BlackjackScreenPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        // Preview con datos de ejemplo (sin ViewModel real)
        BlackjackScreenPreviewContent()
    }
}

/**
 * Contenido de preview con datos estáticos para visualización en Android Studio
 */
@Composable
private fun BlackjackScreenPreviewContent() {
    // Datos de ejemplo para el preview
    val exampleDealerCards = listOf(
        Card(CardValue.EIGHT, CardSuit.DIAMONDS),
        Card(CardValue.JACK, CardSuit.CLUBS)
    )
    val examplePlayerCards = listOf(
        Card(CardValue.SEVEN, CardSuit.CLUBS),
        Card(CardValue.FIVE, CardSuit.HEARTS)
    )
    
    var selectedFooterItem by remember { mutableStateOf("Mesas") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            AppHeader(
                balance = "$5,000.00",
                navController = rememberNavController()
            )
            
            // Título
            BlackjackTitleBar(onBackClick = { })
            
            // Área de juego con contenedor
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                // Contenedor del juego con degradado rojo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(GradientCenter, GradientEdge),
                                radius = 900f
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Dealer Section
                        DealerSection(
                            cards = exampleDealerCards,
                            score = 18,
                            currentBet = 420.0,
                            showScore = true
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Resultado
                        ResultBanner(text = "DEALER GANA 18 A 12", isLoss = true)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Player Section
                        PlayerSection(
                            cards = examplePlayerCards,
                            score = 12,
                            showScore = true
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Turn Indicator
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TurnIndicator(gameState = BlackjackState.PLAYER_TURN)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Action Buttons (en estado de turno del jugador)
                ActionButtons(
                    gameState = BlackjackState.PLAYER_TURN,
                    canDouble = true,
                    canSplit = false,
                    currentBet = 420.0,
                    lastBet = 0.0,
                    onDeal = { },
                    onHit = { },
                    onStand = { },
                    onDouble = { },
                    onSplit = { },
                    onNewRound = { },
                    onRepeatBet = { },
                    onClearBet = { }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Footer
            AppFooter(
                selectedItem = selectedFooterItem,
                onItemSelected = { selectedFooterItem = it },
                navController = rememberNavController()
            )
        }
    }
}

@Preview(showBackground = true, name = "Betting Phase")
@Composable
fun BlackjackBettingPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkBackground)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chips
            ChipSelectorRow(
                selectedChip = 10,
                onChipSelected = { },
                onChipClicked = { }
            )
            
            // Botones de apuesta
            ActionButtons(
                gameState = BlackjackState.BETTING,
                canDouble = false,
                canSplit = false,
                currentBet = 50.0,
                lastBet = 100.0,
                onDeal = { },
                onHit = { },
                onStand = { },
                onDouble = { },
                onSplit = { },
                onNewRound = { },
                onRepeatBet = { },
                onClearBet = { }
            )
        }
    }
}
