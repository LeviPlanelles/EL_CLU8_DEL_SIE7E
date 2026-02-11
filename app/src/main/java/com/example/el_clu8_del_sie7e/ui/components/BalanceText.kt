package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.Tomorrow

/**
 * =====================================================================================
 * BALANCETEXT - COMPONENTE PARA MOSTRAR NÚMEROS CON FUENTE TOMORROW
 * =====================================================================================
 *
 * Este componente se usa para mostrar números monetarios (balance, cantidades, etc.)
 * fuera de los juegos. Usa la fuente Tomorrow que es más adecuada para números.
 *
 * CUÁNDO USAR:
 * - Balance del usuario en el header
 * - Cantidades en depósitos y retiros
 * - Historial de transacciones
 * - Cualquier número monetario fuera de juegos
 *
 * CUÁNDO NO USAR:
 * - Números dentro de juegos (slots, blackjack, ruleta) -> usar Poppins
 * - El logo de la app -> usar Gideon
 * - Texto normal -> usar Poppins (por defecto del tema)
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * BalanceText(
 *     text = "$5,000.00",
 *     fontSize = 14.sp,
 *     color = Color.White
 * )
 * ```
 */
@Composable
fun BalanceText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = Tomorrow,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = textAlign
        )
    )
}

/**
 * Variante más grande del BalanceText para mostrar cantidades destacadas
 * 
 * Uso: Pantallas de depósito/retiro donde el monto es el elemento principal
 */
@Composable
fun BalanceTextLarge(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = Tomorrow,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = textAlign
        )
    )
}

/**
 * Variante pequeña del BalanceText para etiquetas de montos
 * 
 * Uso: Etiquetas junto a campos de entrada, totales secundarios
 */
@Composable
fun BalanceTextSmall(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontFamily = Tomorrow,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = textAlign
        )
    )
}
