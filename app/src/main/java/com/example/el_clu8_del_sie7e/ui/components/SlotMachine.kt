package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.viewmodel.SlotSymbol

/**
 * =====================================================================================
 * SLOTMACHINE.KT - COMPONENTE DE LA MÁQUINA TRAGAPERRAS (VERSIÓN SIMPLIFICADA)
 * =====================================================================================
 *
 * Este componente renderiza la máquina tragaperras con animación simple.
 * Versión optimizada para evitar problemas de rendimiento.
 *
 * CARACTERÍSTICAS:
 * ---------------
 * - 5 rodillos (reels) verticales
 * - 3 símbolos visibles por rodillo
 * - Animación de opacidad durante el giro
 * - Líneas doradas divisorias para marcar la zona ganadora
 *
 * =====================================================================================
 */

// Altura de cada símbolo
private val SYMBOL_HEIGHT = 60.dp
// Altura visible del área de rodillos
private val VISIBLE_HEIGHT = 180.dp

@Composable
fun SlotMachine(
    symbols: List<SlotSymbol>,
    isSpinning: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(VISIBLE_HEIGHT)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1A1A1A))
            .border(
                width = 3.dp,
                color = Color(0xFF3A3A3A),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // ============================================================
        // RODILLOS DE LA TRAGAPERRAS
        // ============================================================
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            symbols.forEachIndexed { index, symbol ->
                // Rodillo individual
                SlotReel(
                    symbol = symbol,
                    isSpinning = isSpinning,
                    reelIndex = index,
                    modifier = Modifier.weight(1f)
                )
                
                // Separador vertical dorado entre rodillos
                if (index < symbols.size - 1) {
                    VerticalDivider(
                        modifier = Modifier.height(VISIBLE_HEIGHT - 20.dp),
                        thickness = 2.dp,
                        color = AccentGold.copy(alpha = 0.5f)
                    )
                }
            }
        }

        // ============================================================
        // LÍNEAS DORADAS HORIZONTALES (ZONA GANADORA)
        // ============================================================
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(SYMBOL_HEIGHT - 10.dp))
            
            HorizontalDivider(
                thickness = 2.dp,
                color = AccentGold
            )
            
            Spacer(modifier = Modifier.height(SYMBOL_HEIGHT + 10.dp))
            
            HorizontalDivider(
                thickness = 2.dp,
                color = AccentGold
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * =====================================================================================
 * SLOTREEL - COMPONENTE DE UN RODILLO INDIVIDUAL (SIMPLIFICADO)
 * =====================================================================================
 *
 * Versión simplificada sin animaciones complejas de offset.
 * Usa animación de opacidad para simular el giro.
 */
@Composable
private fun SlotReel(
    symbol: SlotSymbol,
    isSpinning: Boolean,
    reelIndex: Int,
    modifier: Modifier = Modifier
) {
    // Animación simple de opacidad
    val alpha by animateFloatAsState(
        targetValue = if (isSpinning) 0.3f else 1f,
        animationSpec = tween(durationMillis = 300),
        label = "reelAlpha"
    )
    
    Box(
        modifier = modifier
            .height(VISIBLE_HEIGHT)
            .clip(RoundedCornerShape(8.dp))
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Símbolo superior
            ReelSymbol(
                displayText = if (isSpinning) "?" else getAdjacentSymbol(symbol, -1).emoji,
                isCenter = false
            )
            
            // Símbolo central (RESULTADO)
            ReelSymbol(
                displayText = if (isSpinning) "?" else symbol.emoji,
                isCenter = true
            )
            
            // Símbolo inferior
            ReelSymbol(
                displayText = if (isSpinning) "?" else getAdjacentSymbol(symbol, 1).emoji,
                isCenter = false
            )
        }
    }
}

/**
 * Obtiene un símbolo adyacente para mostrar arriba o abajo del principal
 */
private fun getAdjacentSymbol(current: SlotSymbol, offset: Int): SlotSymbol {
    val symbols = SlotSymbol.entries.filter { it != SlotSymbol.SEVEN }
    val currentIndex = symbols.indexOf(current).takeIf { it >= 0 } ?: 0
    val newIndex = (currentIndex + offset + symbols.size) % symbols.size
    return symbols[newIndex]
}

/**
 * =====================================================================================
 * REELSYMBOL - COMPONENTE DE UN SÍMBOLO EN EL RODILLO
 * =====================================================================================
 */
@Composable
private fun ReelSymbol(
    displayText: String,
    isCenter: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SYMBOL_HEIGHT)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .background(
                color = if (isCenter) Color(0xFF2A2A2A) else Color(0xFF1E1E1E),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            fontSize = if (isCenter) 36.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
