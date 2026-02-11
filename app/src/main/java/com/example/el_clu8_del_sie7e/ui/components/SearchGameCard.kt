package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * SEARCHGAMECARD.KT - COMPONENTE DE TARJETA DE JUEGO PARA BUSCADOR
 * =====================================================================================
 *
 * Este componente muestra una tarjeta horizontal para cada juego en el buscador.
 * Diseñado específicamente para la pantalla de búsqueda de juegos.
 *
 * DISEÑO:
 * -------
 * - Imagen del juego a la izquierda (cuadrada, redondeada)
 * - Información del juego en el centro (nombre, rating, categoría)
 * - Botón "JUGAR" rojo a la derecha
 * - Fondo oscuro con borde sutil
 *
 * PARÁMETROS:
 * -----------
 * @param gameImage ID del recurso drawable de la imagen del juego
 * @param gameName Nombre del juego (ej: "ROYAL DICE VIP")
 * @param rating Rating del juego (ej: 4.9)
 * @param category Categoría del juego (ej: "Dados", "Cartas", "En Vivo")
 * @param onPlayClick Acción al presionar "JUGAR"
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun SearchGameCard(
    gameImage: Int,
    gameName: String,
    rating: Double,
    category: String,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4A1618), // Rojo oscuro inicio
                        Color(0xFF2A0C0D)  // Rojo muy oscuro final
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF6A2622),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ===================================================================
            // SECCIÓN IZQUIERDA: IMAGEN DEL JUEGO
            // ===================================================================
            Image(
                painter = painterResource(id = gameImage),
                contentDescription = gameName,
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // ===================================================================
            // SECCIÓN CENTRAL: INFORMACIÓN DEL JUEGO
            // ===================================================================
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Nombre del juego
                Text(
                    text = gameName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating con estrella
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = AccentGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rating.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• $category",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        fontFamily = Poppins
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // ===================================================================
            // SECCIÓN DERECHA: BOTÓN JUGAR
            // ===================================================================
            RedButton(
                text = "JUGAR",
                onClick = onPlayClick,
                showArrow = false,
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp)
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SearchGameCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchGameCard(
                gameImage = R.drawable.game_royal_dice_vip,
                gameName = "ROYAL DICE VIP",
                rating = 4.9,
                category = "Dados",
                onPlayClick = {}
            )
            SearchGameCard(
                gameImage = R.drawable.game_texas_holdem_poker,
                gameName = "TEXAS HOLDEM POKER",
                rating = 4.8,
                category = "Cartas",
                onPlayClick = {}
            )
        }
    }
}
