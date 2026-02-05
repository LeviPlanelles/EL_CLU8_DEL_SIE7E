package com.example.el_clu8_del_sie7e.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.el_clu8_del_sie7e.ui.components.*
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.DarkBackground
import com.example.el_clu8_del_sie7e.viewmodel.BalanceViewModel
import com.example.el_clu8_del_sie7e.viewmodel.GameState
import com.example.el_clu8_del_sie7e.viewmodel.SlotGameViewModel
import kotlinx.coroutines.delay

/**
 * =====================================================================================
 * SLOTGAMEPLAYSCREEN_ANIMATED.KT - VERSIÓN CON ANIMACIONES LOCAS DE CASINO
 * =====================================================================================
 *
 * Esta es una versión mejorada del SlotGamePlayScreen que incluye animaciones
 * espectaculares tipo casino real:
 *
 * EFECTOS AGREGADOS:
 * - Confeti dorado al ganar
 * - Destellos/brillos en victoria
 * - Rebote elástico de símbolos al detenerse
 * - Efecto de blur al girar
 * - Temblor en derrota
 * - Explosión de monedas en jackpot
 * - Rayos eléctricos en victorias grandes
 * - Aura de brillo en símbolos ganadores
 *
 * TODAS las animaciones son nativas de Compose (sin Lottie externo)
 * =====================================================================================
 */

@Composable
fun SlotGamePlayScreenAnimated(
    navController: NavController,
    slotName: String,
    balanceViewModel: BalanceViewModel,
    slotGameViewModel: SlotGameViewModel,
    modifier: Modifier = Modifier
) {
    // Estados del ViewModel
    val gameState by slotGameViewModel.gameState.collectAsState()
    val lastWinAmount by slotGameViewModel.lastWinAmount.collectAsState()
    
    // Estados para efectos visuales
    var showConfetti by remember { mutableStateOf(false) }
    var showSparkles by remember { mutableStateOf(false) }
    var showCoinSplash by remember { mutableStateOf(false) }
    var showLightning by remember { mutableStateOf(false) }
    var isShaking by remember { mutableStateOf(false) }
    var showGlow by remember { mutableStateOf(false) }
    
    // Detectar cambios de estado para activar efectos
    LaunchedEffect(gameState) {
        when (gameState) {
            GameState.WIN -> {
                // Activar efectos de victoria
                showGlow = true
                showSparkles = true
                showConfetti = true
                
                // Si es victoria grande, mostrar más efectos
                if (lastWinAmount >= 50) {
                    showCoinSplash = true
                }
                if (lastWinAmount >= 100) {
                    showLightning = true
                }
                
                // Las animaciones duran 3 segundos y luego se detienen automáticamente
                delay(3000)
                
                // Detener todos los efectos
                showConfetti = false
                showSparkles = false
                showCoinSplash = false
                showLightning = false
                showGlow = false
            }
            GameState.LOSE -> {
                // Efecto de temblor en derrota (dura 500ms)
                isShaking = true
                delay(500)
                isShaking = false
            }
            GameState.SPINNING -> {
                // Resetear efectos al empezar a girar
                showConfetti = false
                showSparkles = false
                showCoinSplash = false
                showLightning = false
                showGlow = false
            }
            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // FONDO CON GRADIENTE ANIMADO
        AnimatedBackground(isActive = gameState == GameState.SPINNING)
        
        // CONTENIDO PRINCIPAL DEL JUEGO
        ShakeEffect(isShaking = isShaking) {
            SlotGamePlayScreen(
                navController = navController,
                slotName = slotName,
                balanceViewModel = balanceViewModel,
                slotGameViewModel = slotGameViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // EFECTOS VISUALES SUPERPUESTOS
        // ================================
        
        // 1. Aura de brillo dorado
        if (showGlow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                GlowEffect(
                    isActive = true,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        
        // 2. Confeti dorado
        ConfettiEffect(
            isVisible = showConfetti,
            modifier = Modifier.fillMaxSize(),
            particleCount = 150
        )
        
        // 3. Destellos brillantes
        SparkleEffect(
            isVisible = showSparkles,
            modifier = Modifier.fillMaxSize()
        )
        
        // 4. Explosión de monedas
        CoinSplashEffect(
            isVisible = showCoinSplash,
            modifier = Modifier.fillMaxSize(),
            coinCount = 40
        )
        
        // 5. Rayos eléctricos (victorias grandes)
        if (showLightning) {
            LightningEffect(
                isVisible = true,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // 6. Overlay de flash blanco para impacto
        WinFlashEffect(gameState = gameState, winAmount = lastWinAmount)
    }
}

/**
 * Fondo con gradiente animado que cambia mientras gira
 */
@Composable
private fun AnimatedBackground(isActive: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    
    val hueShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hue"
    )

    val backgroundColor = if (isActive) {
        // Fondo más brillante y dinámico cuando gira
        Brush.radialGradient(
            colors = listOf(
                Color(0xFF2A1A0A),
                DarkBackground,
                Color(0xFF1A0A1A)
            )
        )
    } else {
        // Fondo normal
        Brush.radialGradient(
            colors = listOf(
                DarkBackground,
                Color(0xFF151515)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    )
}

/**
 * Efecto de flash blanco rápido al ganar (impacto visual)
 */
@Composable
private fun WinFlashEffect(gameState: GameState, winAmount: Double) {
    if (gameState != GameState.WIN) return
    
    var showFlash by remember { mutableStateOf(true) }
    
    LaunchedEffect(gameState) {
        delay(150)
        showFlash = false
    }
    
    if (showFlash) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFFFD700).copy(alpha = 0.3f)
                )
        )
    }
}

/**
 * Extensión para facilitar la integración
 * Puedes usar esta función en lugar de SlotGamePlayScreen directamente
 */
@Composable
fun SlotGamePlayScreenWithEffects(
    navController: NavController,
    slotName: String,
    balanceViewModel: BalanceViewModel,
    slotGameViewModel: SlotGameViewModel
) {
    SlotGamePlayScreenAnimated(
        navController = navController,
        slotName = slotName,
        balanceViewModel = balanceViewModel,
        slotGameViewModel = slotGameViewModel
    )
}
