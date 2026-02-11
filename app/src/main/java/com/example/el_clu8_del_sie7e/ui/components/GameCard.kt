package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/**
 * =====================================================================================
 * GAMECARD.KT - COMPONENTE DE TARJETA DE JUEGO
 * =====================================================================================
 *
 * Este componente muestra una tarjeta individual para cada juego disponible
 * en el lobby del casino. Cada tarjeta incluye:
 * - Un icono representativo del juego
 * - Nombre del juego
 * - Cantidad de variantes disponibles
 * - Boton ROJO para jugar
 *
 * DISEÑO:
 * -------
 * - Fondo oscuro con gradiente para simular imagen del juego
 * - Borde dorado sutil
 * - Icono del juego en la esquina superior izquierda
 * - Boton rojo de accion en la parte inferior
 *
 * PARAMETROS:
 * -----------
 * @param gameIcon ID del recurso drawable del icono del juego
 * @param gameName Nombre del juego (ej: "Slot", "Roulette", "BlackJack")
 * @param gameCount Cantidad de variantes (ej: "Más de 200 juegos")
 * @param onPlayClick Accion al presionar "JUGAR AHORA"
 * @param backgroundImage ID del recurso drawable de la imagen de fondo (opcional)
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun GameCard(
    gameIcon: Int,
    gameName: String,
    gameCount: String,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundImage: Int? = null
) {
    Box(
        modifier = modifier.height(180.dp)
    ) {
        // CAPA 1: Imagen de fondo TAL CUAL (sin overlay, sin borde - la imagen ya lo tiene)
        if (backgroundImage != null) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds // Muestra toda la imagen sin recortar los bordes
            )
        } else {
            // Solo si no hay imagen, usar color de fondo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFF1E1410),
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }

        // CAPA 2: Contenido superpuesto (iconos, texto, botones)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(bottom = 8.dp), // Padding extra en el fondo para subir el botón
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Seccion superior: Icono del juego
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = AccentGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = gameIcon),
                    contentDescription = gameName,
                    tint = AccentGold,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Seccion inferior: Info y boton
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Nombre del juego
                Text(
                    text = gameName,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )

                // Cantidad de juegos
                Text(
                    text = gameCount,
                    color = Color.Gray,
                    fontSize = 11.sp,
                    fontFamily = Poppins
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Boton ROJO de jugar (sin flecha)
                RedButton(
                    text = "JUGAR AHORA",
                    onClick = onPlayClick,
                    showArrow = false,
                    modifier = Modifier.height(36.dp)
                )
            }
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun GameCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        GameCard(
            gameIcon = R.drawable.ic_cards,
            gameName = "BlackJack",
            gameCount = "Más de 200 juegos",
            onPlayClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
