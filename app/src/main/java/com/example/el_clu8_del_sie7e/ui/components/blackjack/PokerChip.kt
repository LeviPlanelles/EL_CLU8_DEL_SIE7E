package com.example.el_clu8_del_sie7e.ui.components.blackjack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Colores de las fichas de casino
 */
enum class ChipColor(
    val mainColor: Color,
    val darkColor: Color,
    val borderColor: Color
) {
    BLACK(
        mainColor = Color(0xFF2D2D2D),
        darkColor = Color(0xFF1A1A1A),
        borderColor = Color(0xFF4A4A4A)
    ),
    BLUE(
        mainColor = Color(0xFF1E5AA8),
        darkColor = Color(0xFF143D75),
        borderColor = Color(0xFF2B7FE0)
    ),
    RED(
        mainColor = Color(0xFFC41E3A),
        darkColor = Color(0xFF8B1528),
        borderColor = Color(0xFFE8364F)
    ),
    GREEN(
        mainColor = Color(0xFF228B22),
        darkColor = Color(0xFF165E16),
        borderColor = Color(0xFF2DB82D)
    ),
    GOLD(
        mainColor = Color(0xFFDAA520),
        darkColor = Color(0xFFB8860B),
        borderColor = Color(0xFFFFD700)
    )
}

/**
 * Ficha de casino/poker
 * 
 * @param value Valor de la ficha ($1, $10, $100, etc)
 * @param chipColor Color de la ficha
 * @param size Tamaño de la ficha
 * @param showLabel Si mostrar el valor en la ficha pequeña flotante
 */
@Composable
fun PokerChip(
    value: String,
    chipColor: ChipColor,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    showLabel: Boolean = false,
    labelText: String = ""
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Ficha principal
        Box(
            modifier = Modifier
                .size(size)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(chipColor.mainColor, chipColor.darkColor)
                    )
                )
                .border(3.dp, chipColor.borderColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Borde interior decorativo
            Box(
                modifier = Modifier
                    .size(size * 0.75f)
                    .border(2.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Valor de la ficha
                Text(
                    text = value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.32f).sp
                )
            }
        }
        
        // Etiqueta flotante (para mostrar el valor encima)
        if (showLabel && labelText.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .offset(y = -(size / 2 + 8.dp))
                    .background(Color(0xFF2D2D2D), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = labelText,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Grupo de fichas para selección de apuesta
 */
@Composable
fun ChipSelector(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy((-8).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PokerChip(
            value = "\$1",
            chipColor = ChipColor.BLACK,
            size = 48.dp
        )
        PokerChip(
            value = "\$10",
            chipColor = ChipColor.BLUE,
            size = 52.dp
        )
        // Espacio para "TU TURNO"
        Spacer(modifier = Modifier.width(80.dp))
        PokerChip(
            value = "\$100",
            chipColor = ChipColor.RED,
            size = 52.dp
        )
        PokerChip(
            value = "\$200",
            chipColor = ChipColor.GREEN,
            size = 48.dp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
fun PokerChipPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PokerChip(value = "\$1", chipColor = ChipColor.BLACK)
        PokerChip(value = "\$10", chipColor = ChipColor.BLUE)
        PokerChip(value = "\$100", chipColor = ChipColor.RED)
        PokerChip(value = "\$200", chipColor = ChipColor.GREEN)
    }
}
