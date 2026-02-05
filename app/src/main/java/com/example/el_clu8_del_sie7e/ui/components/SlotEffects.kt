package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

/**
 * =====================================================================================
 * CONFETTIEFFECT.KT - EFECTO DE CONFETI PARA VICTORIAS
 * =====================================================================================
 *
 * Componente que muestra una explosión de confeti dorado y colores cuando el jugador gana.
 * Similar a las animaciones de victoria en casinos reales.
 *
 * CARACTERÍSTICAS:
 * - Partículas de confeti con diferentes formas y colores
 * - Física de caída realista con gravedad
 * - Rotación de partículas mientras caen
 * - Efecto de desvanecimiento gradual
 * - Colores dorados, rojos y verdes (tema casino)
 *
 * USO:
 * ```kotlin
 * ConfettiEffect(
 *     isVisible = showWinConfetti,
 *     modifier = Modifier.fillMaxSize()
 * )
 * ```
 * =====================================================================================
 */

// Colores del confeti (tema casino)
private val ConfettiColors = listOf(
    Color(0xFFFFD700), // Dorado brillante
    Color(0xFFFFA000), // Dorado oscuro
    Color(0xFFFF4444), // Rojo
    Color(0xFF4CAF50), // Verde
    Color(0xFFFFFFFF), // Blanco
    Color(0xFFFFC107), // Ámbar
)

// Formas de confeti
private enum class ConfettiShape {
    RECTANGLE, CIRCLE, TRIANGLE
}

// Datos de cada partícula de confeti
private data class ConfettiParticle(
    var x: Float,
    var y: Float,
    var rotation: Float,
    var rotationSpeed: Float,
    var velocityX: Float,
    var velocityY: Float,
    val color: Color,
    val shape: ConfettiShape,
    val size: Float,
    var alpha: Float = 1f,
    var gravity: Float = 0.3f
)

@Composable
fun ConfettiEffect(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    particleCount: Int = 100
) {
    if (!isVisible) return

    // Estado de las partículas
    var particles by remember { mutableStateOf<List<ConfettiParticle>>(emptyList()) }
    
    // Animación del tiempo para actualizar física
    val infiniteTransition = rememberInfiniteTransition(label = "confetti")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(16, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    // Inicializar partículas
    LaunchedEffect(isVisible) {
        if (isVisible) {
            particles = List(particleCount) {
                ConfettiParticle(
                    x = 0.5f + Random.nextFloat() * 0.4f - 0.2f, // Centro con dispersión
                    y = 0.3f, // Empezar desde arriba
                    rotation = Random.nextFloat() * 360f,
                    rotationSpeed = Random.nextFloat() * 10f - 5f,
                    velocityX = Random.nextFloat() * 0.02f - 0.01f,
                    velocityY = Random.nextFloat() * 0.02f + 0.01f,
                    color = ConfettiColors.random(),
                    shape = ConfettiShape.values().random(),
                    size = Random.nextFloat() * 15f + 8f
                )
            }
            
            // Detener después de 3 segundos
            delay(3000)
            particles = emptyList()
        }
    }

    // Actualizar física de partículas
    LaunchedEffect(time) {
        particles = particles.map { particle ->
            particle.apply {
                x += velocityX
                y += velocityY
                velocityY += gravity * 0.001f
                rotation += rotationSpeed
                alpha = max(0f, alpha - 0.005f)
                
                // Rebote en los bordes
                if (x < 0f || x > 1f) velocityX *= -0.8f
            }
        }.filter { it.alpha > 0f && it.y < 1.5f }
    }

    // Dibujar confeti
    Canvas(modifier = modifier) {
        particles.forEach { particle ->
            drawConfettiParticle(particle, size.width, size.height)
        }
    }
}

private fun DrawScope.drawConfettiParticle(
    particle: ConfettiParticle,
    canvasWidth: Float,
    canvasHeight: Float
) {
    val x = particle.x * canvasWidth
    val y = particle.y * canvasHeight
    
    withTransform({
        rotate(particle.rotation, Offset(x, y))
    }) {
        when (particle.shape) {
            ConfettiShape.RECTANGLE -> {
                drawRect(
                    color = particle.color.copy(alpha = particle.alpha),
                    topLeft = Offset(x - particle.size/2, y - particle.size/4),
                    size = androidx.compose.ui.geometry.Size(particle.size, particle.size/2)
                )
            }
            ConfettiShape.CIRCLE -> {
                drawCircle(
                    color = particle.color.copy(alpha = particle.alpha),
                    radius = particle.size/2,
                    center = Offset(x, y)
                )
            }
            ConfettiShape.TRIANGLE -> {
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(x, y - particle.size/2)
                    lineTo(x - particle.size/2, y + particle.size/2)
                    lineTo(x + particle.size/2, y + particle.size/2)
                    close()
                }
                drawPath(
                    path = path,
                    color = particle.color.copy(alpha = particle.alpha)
                )
            }
        }
    }
}

/**
 * =====================================================================================
 * SPARKLEEFFECT.KT - EFECTO DE DESTELLOS/BRILLOS
 * =====================================================================================
 *
 * Crea destellos de luz dorada tipo casino cuando hay victoria.
 * Efecto de estrellas brillantes que aparecen y desaparecen.
 * =====================================================================================
 */

// Data class para destellos (definida a nivel de archivo para ser accesible)
private data class Sparkle(
    var x: Float,
    var y: Float,
    var scale: Float,
    var alpha: Float,
    var rotation: Float
)

@Composable
fun SparkleEffect(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    var sparkles by remember { mutableStateOf<List<Sparkle>>(emptyList()) }

    // Animación
    val infiniteTransition = rememberInfiniteTransition(label = "sparkle")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(50, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sparkle_time"
    )

    // Crear nuevos destellos periódicamente
    LaunchedEffect(time) {
        if (Random.nextFloat() < 0.15f) {
            sparkles = sparkles + Sparkle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                scale = 0f,
                alpha = 1f,
                rotation = Random.nextFloat() * 360f
            )
        }
        
        // Actualizar destellos existentes
        sparkles = sparkles.map { sparkle ->
            sparkle.apply {
                scale += 0.05f
                alpha -= 0.03f
            }
        }.filter { it.alpha > 0f }
    }

    // Detener después de 2.5 segundos
    LaunchedEffect(isVisible) {
        delay(2500)
        sparkles = emptyList()
    }

    Canvas(modifier = modifier) {
        sparkles.forEach { sparkle ->
            drawSparkle(sparkle, size.width, size.height)
        }
    }
}

private fun DrawScope.drawSparkle(
    sparkle: Sparkle,
    canvasWidth: Float,
    canvasHeight: Float
) {
    val x = sparkle.x * canvasWidth
    val y = sparkle.y * canvasHeight
    val color = Color(0xFFFFD700).copy(alpha = sparkle.alpha)
    val scaleValue: Float = sparkle.scale
    
    withTransform({
        rotate(sparkle.rotation, Offset(x, y))
    }) {
        // Dibujar estrella de 4 puntas
        val path = androidx.compose.ui.graphics.Path().apply {
            // Centro
            moveTo(x, y)
            // Arriba
            lineTo(x, y - 20f * scaleValue)
            lineTo(x + 3f, y - 3f)
            // Derecha
            lineTo(x + 20f * scaleValue, y)
            lineTo(x + 3f, y + 3f)
            // Abajo
            lineTo(x, y + 20f * scaleValue)
            lineTo(x - 3f, y + 3f)
            // Izquierda
            lineTo(x - 20f * scaleValue, y)
            lineTo(x - 3f, y - 3f)
            close()
        }
        
        drawPath(
            path = path,
            color = color,
            style = androidx.compose.ui.graphics.drawscope.Fill
        )
    }
}

/**
 * =====================================================================================
 * SHOKEFFECT.KT - EFECTO DE TEMBLOR PARA DERROTAS
 * =====================================================================================
 *
 * Efecto de vibración/temblor tipo máquina tragamonedas cuando se pierde.
 * Similar al efecto de "no win" en casinos reales.
 * =====================================================================================
 */

@Composable
fun ShakeEffect(
    isShaking: Boolean,
    content: @Composable () -> Unit
) {
    val shakeOffset by animateFloatAsState(
        targetValue = if (isShaking) 1f else 0f,
        animationSpec = tween(50),
        label = "shake"
    )

    val offsetX = if (isShaking) {
        Random.nextFloat() * 10f - 5f
    } else 0f

    val offsetY = if (isShaking) {
        Random.nextFloat() * 10f - 5f
    } else 0f

    Box(
        modifier = Modifier.offset(
            x = offsetX.dp,
            y = offsetY.dp
        )
    ) {
        content()
    }
}

/**
 * =====================================================================================
 * WINPULSEANIMATION.KT - PULSACIÓN DE VICTORIA
 * =====================================================================================
 *
 * Efecto de pulsación expansiva dorada que rodea los símbolos ganadores.
 * Similar al efecto de resaltado en casinos.
 * =====================================================================================
 */

@Composable
fun WinPulseAnimation(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isActive) return

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Canvas(modifier = modifier) {
        drawCircle(
            color = Color(0xFFFFD700).copy(alpha = alpha),
            radius = (size.minDimension / 2) * scale,
            center = Offset(size.width / 2, size.height / 2),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f)
        )
    }
}

/**
 * =====================================================================================
 * SLOTREELBLUREFFECT.KT - EFECTO DE BLUR AL GIRAR
 * =====================================================================================
 *
 * Crea un efecto de desenfoque de movimiento cuando los carriles giran rápido.
 * Simula el efecto de velocidad de las máquinas tragamonedas reales.
 * =====================================================================================
 */

@Composable
fun SlotReelBlurEffect(
    isSpinning: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isSpinning) return

    val infiniteTransition = rememberInfiniteTransition(label = "blur")
    
    val blurAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blur_alpha"
    )

    Canvas(modifier = modifier) {
        // Efecto de líneas de movimiento
        val lineCount = 8
        for (i in 0 until lineCount) {
            val y = (size.height / lineCount) * i
            drawLine(
                color = Color.White.copy(alpha = blurAlpha * 0.5f),
                start = Offset(0f, y),
                end = Offset(size.width, y + 20f),
                strokeWidth = 3f
            )
        }
    }
}

/**
 * =====================================================================================
 * GLOWEFFECT.KT - EFECTO DE BRILLO DORADO
 * =====================================================================================
 *
 * Crea un aura de brillo dorado alrededor de elementos importantes.
 * Perfecto para resaltar símbolos especiales o victorias.
 * =====================================================================================
 */

@Composable
fun GlowEffect(
    isActive: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFFD700)
) {
    if (!isActive) return

    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    Canvas(modifier = modifier) {
        // Aura exterior
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = 0.4f),
                    color.copy(alpha = 0.1f),
                    Color.Transparent
                ),
                center = Offset(size.width / 2, size.height / 2),
                radius = (size.minDimension / 2) * glowScale
            ),
            radius = (size.minDimension / 2) * glowScale,
            center = Offset(size.width / 2, size.height / 2)
        )
    }
}
