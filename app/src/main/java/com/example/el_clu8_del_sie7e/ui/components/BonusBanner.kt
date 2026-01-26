package com.example.el_clu8_del_sie7e.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AccentGold.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Imagen de fondo (si existe)
        if (backgroundImage != null) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Overlay oscuro semitransparente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (backgroundImage != null) {
                            listOf(
                                Color(0xAA1E1410),
                                Color(0xDD1E1410)
                            )
                        } else {
                            listOf(
                                Color(0xFF3D2819),
                                Color(0xFF2A1810)
                            )
                        }
                    )
                )
        )

        // Contenido del banner
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Badge "EXCLUSIVO" - Esquina superior izquierda
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(
                    color = Color(0xFFB8860B),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = badgeText,
                color = Color.Black,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Icono estrella dorada - Esquina superior derecha
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(32.dp)
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
                modifier = Modifier.size(18.dp)
            )
        }

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Titulo
            Text(
                text = title,
                color = AccentGold,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Subtitulo
            Text(
                text = subtitle,
                color = Color.White,
                fontSize = 13.sp
            )

            // Boton de accion ROJO con flecha
            RedButton(
                text = buttonText,
                onClick = onButtonClick,
                showArrow = true
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
