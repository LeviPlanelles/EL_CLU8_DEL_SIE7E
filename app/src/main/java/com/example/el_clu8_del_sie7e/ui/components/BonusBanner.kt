package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/**
 * =====================================================================================
 * BONUSBANNER.KT - COMPONENTE DE BANNER DE BONOS Y PROMOCIONES
 * =====================================================================================
 *
 * Este componente muestra un banner destacado para promociones, bonos de bienvenida
 * y ofertas especiales en la pantalla de lobby.
 *
 * CARACTERISTICAS:
 * ----------------
 * - Fondo oscuro con gradiente sutil (simula imagen de fondo)
 * - Borde dorado para destacar
 * - Badge de "EXCLUSIVO" en la esquina superior izquierda
 * - Icono estrella dorada en la esquina superior derecha
 * - Boton rojo de accion con flecha
 *
 * PARAMETROS:
 * -----------
 * @param title Titulo principal del banner (ej: "BONO DE BIENVENIDA")
 * @param subtitle Subtitulo descriptivo (ej: "100% hasta $500 en tu primer deposito")
 * @param badgeText Texto del badge (ej: "EXCLUSIVO")
 * @param buttonText Texto del boton de accion (ej: "RECLAMAR AHORA")
 * @param onButtonClick Accion al presionar el boton
 * @param backgroundImage ID del recurso drawable de la imagen de fondo (opcional)
 * @param modifier Modificador opcional
 *
 * =====================================================================================
 */
@Composable
fun BonusBanner(
    title: String,
    subtitle: String,
    badgeText: String = "EXCLUSIVO",
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundImage: Int? = null
) {
    Box(
        modifier = modifier.height(180.dp) // Altura reducida a 180dp para mejor proporción
    ) {
        // CAPA 1: Imagen de fondo TAL CUAL (sin overlay, sin borde - la imagen ya lo tiene)
        if (backgroundImage != null) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth // Cambiado a FillWidth para mejor proporción
            )
        } else {
            // Solo si no hay imagen, usar color de fondo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFF2A1810),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }

        // CAPA 2: Contenido superpuesto (badge, estrella, texto, botón)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp) // Reducido de 16dp a 12dp
                .padding(bottom = 8.dp) // Padding extra en el fondo para margen del botón
        ) {
            // Badge "EXCLUSIVO" - Esquina superior izquierda
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(
                        color = Color(0xFFB8860B),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 3.dp) // Reducido el padding
            ) {
                Text(
                    text = badgeText,
                    color = Color.Black,
                    fontSize = 9.sp, // Reducido de 10sp a 9sp
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
            }

            // Icono estrella dorada - Esquina superior derecha
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(28.dp) // Reducido de 32dp a 28dp
                    .background(
                        color = AccentGold,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Estrella",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp) // Reducido de 18dp a 16dp
                )
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(6.dp) // Reducido de 8dp a 6dp
            ) {
                // Titulo
                Text(
                    text = title,
                    color = AccentGold,
                    fontSize = 18.sp, // Reducido de 20sp a 18sp
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )

                // Subtitulo
                Text(
                    text = subtitle,
                    color = Color.White,
                    fontSize = 12.sp, // Reducido de 13sp a 12sp
                    fontFamily = Poppins
                )

                Spacer(modifier = Modifier.height(2.dp)) // Reducido de 4dp a 2dp

                // Boton de accion ROJO con flecha
                RedButton(
                    text = buttonText,
                    onClick = onButtonClick,
                    showArrow = true,
                    modifier = Modifier.height(38.dp) // Altura del botón ajustada
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
fun BonusBannerPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        BonusBanner(
            title = "BONO DE BIENVENIDA",
            subtitle = "100% hasta \$500 en tu primer depósito.",
            badgeText = "EXCLUSIVO",
            buttonText = "RECLAMAR AHORA",
            onButtonClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
