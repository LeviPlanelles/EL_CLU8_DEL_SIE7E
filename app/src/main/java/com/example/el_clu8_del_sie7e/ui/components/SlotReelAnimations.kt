package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * =====================================================================================
 * REELBOUNCEANIMATION.KT - ANIMACIÓN DE REBOTE DE SÍMBOLOS
 * =====================================================================================
 *
 * Efecto de rebote elástico cuando un símbolo se detiene en su posición.
 * Similar al efecto de "landing" en casinos reales donde los símbolos hacen
 * un pequeño brinco al detenerse.
 *
 * CARACTERÍSTICAS:
 * - Rebote elástico con física de resorte
 * - Escala temporal para crear efecto de impacto
 * - Desplazamiento Y para simular brinco
 * - Diferentes intensidades según el tipo de símbolo
 * =====================================================================================
 */

@Composable
fun ReelBounceAnimation(
    isRevealed: Boolean,
    isWinning: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Animación de escala para rebote
    val scale by animateFloatAsState(
        targetValue = if (isRevealed) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "bounce_scale"
    )

    // Animación de offset Y para brinco
    val offsetY by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 20f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "bounce_offset"
    )

    // Si es símbolo ganador, agregar animación adicional
    val winningScale by animateFloatAsState(
        targetValue = if (isWinning && isRevealed) 1.1f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "winning_scale"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                this.scaleX = scale * winningScale
                this.scaleY = scale * winningScale
                this.translationY = offsetY
            }
    ) {
        content()
    }
}

/**
 * =====================================================================================
 * SLOTSPINNINGANIMATION.KT - ANIMACIÓN DE GIRO CONTINUO
 * =====================================================================================
 *
 * Simula el efecto de símbolos girando rápidamente mientras el carril está en movimiento.
 * Crea la ilusión de velocidad y movimiento.
 * =====================================================================================
 */

@Composable
fun SlotSpinningAnimation(
    isSpinning: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spin")
    
    // Rotación rápida mientras gira
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spin_rotation"
    )

    // Efecto de blur simulado con alpha parpadeante
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "spin_alpha"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                if (isSpinning) {
                    this.rotationY = rotation
                    this.alpha = alpha
                } else {
                    this.rotationY = 0f
                    this.alpha = 1f
                }
            }
    ) {
        content()
    }
}

/**
 * =====================================================================================
 * COINSPLASHEFFECT.KT - EFECTO DE EXPLOSIÓN DE MONEDAS
 * =====================================================================================
 *
 * Similar al confeti pero específicamente diseñado para monedas doradas.
 * Perfecto para victorias grandes o jackpot.
 * =====================================================================================
 */

@Composable
fun CoinSplashEffect(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    coinCount: Int = 30
) {
    if (!isVisible) return

    data class Coin(
        var x: Float,
        var y: Float,
        var velocityY: Float,
        var velocityX: Float,
        var rotation: Float,
        var scale: Float,
        var alpha: Float
    )

    var coins by remember { mutableStateOf<List<Coin>>(emptyList()) }

    // Inicializar monedas
    LaunchedEffect(isVisible) {
        if (isVisible) {
            coins = List(coinCount) {
                Coin(
                    x = 0.5f + Random.nextFloat() * 0.3f - 0.15f,
                    y = 0.5f,
                    velocityY = -Random.nextFloat() * 0.04f - 0.02f,
                    velocityX = Random.nextFloat() * 0.03f - 0.015f,
                    rotation = Random.nextFloat() * 360f,
                    scale = Random.nextFloat() * 0.5f + 0.5f,
                    alpha = 1f
                )
            }
            
            delay(2000)
            coins = emptyList()
        }
    }

    // Animación de física
    val infiniteTransition = rememberInfiniteTransition(label = "coin_physics")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(16, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "coin_time"
    )

    LaunchedEffect(time) {
        coins = coins.map { coin ->
            coin.apply {
                x += velocityX
                y += velocityY
                velocityY += 0.001f // Gravedad
                rotation += 5f
                alpha -= 0.008f
                
                if (y > 1.1f) alpha = 0f
            }
        }.filter { it.alpha > 0f }
    }

    androidx.compose.foundation.Canvas(modifier = modifier) {
        coins.forEach { coin ->
            val x = coin.x * size.width
            val y = coin.y * size.height
            
            // Dibujar moneda dorada
            drawCircle(
                color = androidx.compose.ui.graphics.Color(0xFFFFD700).copy(alpha = coin.alpha),
                radius = 15f * coin.scale,
                center = androidx.compose.ui.geometry.Offset(x, y)
            )
            
            // Borde de la moneda
            drawCircle(
                color = androidx.compose.ui.graphics.Color(0xFFFFA000).copy(alpha = coin.alpha),
                radius = 12f * coin.scale,
                center = androidx.compose.ui.geometry.Offset(x, y),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
            )
            
            // Símbolo "$"
            // Nota: No podemos dibujar texto fácilmente en Canvas sin TextMeasurer
            // así que solo dibujamos el círculo dorado
        }
    }
}

/**
 * =====================================================================================
 * LIGHTNINGEFFECT.KT - EFECTO DE RAYOS PARA VICTORIAS GRANDES
 * =====================================================================================
 *
 * Efecto de rayos eléctricos que cruzan la pantalla en victorias especiales.
 * Perfecto para jackpot o ganancias grandes.
 * =====================================================================================
 */

@Composable
fun LightningEffect(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    val infiniteTransition = rememberInfiniteTransition(label = "lightning")
    
    val flashAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(0)
        ),
        label = "flash"
    )

    LaunchedEffect(isVisible) {
        delay(500)
    }

    if (flashAlpha > 0.5f) {
        androidx.compose.foundation.Canvas(modifier = modifier) {
            // Flash de fondo
            drawRect(
                color = androidx.compose.ui.graphics.Color(0xFFFFD700).copy(alpha = 0.3f)
            )
            
            // Rayos aleatorios
            repeat(5) { i ->
                val startX = Random.nextFloat() * size.width
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(startX, 0f)
                    var currentX = startX
                    var currentY = 0f
                    
                    while (currentY < size.height) {
                        currentX += Random.nextFloat() * 100f - 50f
                        currentY += Random.nextFloat() * 100f + 50f
                        lineTo(currentX, currentY.coerceAtMost(size.height))
                    }
                }
                
                drawPath(
                    path = path,
                    color = androidx.compose.ui.graphics.Color(0xFFFFEB3B),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                )
            }
        }
    }
}
