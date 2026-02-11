package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.Poppins

/**
 * =====================================================================================
 * SLOTGAMECARD.KT - COMPONENTE DE TARJETA DE SLOT
 * =====================================================================================
 *
 * Este componente muestra una tarjeta de juego de slot con:
 * - Imagen del juego que ocupa toda la tarjeta
 * - Badge opcional (HOT, NUEVO, JACKPOT) en esquina superior derecha
 * - Nombre del juego sobre la imagen (parte inferior)
 * - Botón "JUGAR" con gradiente naranja/dorado
 *
 * DISEÑO (fiel a la imagen):
 * --------------------------
 * - La imagen ocupa todo el espacio de la tarjeta
 * - Gradiente MUY sutil en la parte inferior para legibilidad del texto
 * - Nombre del juego en blanco sobre la imagen
 * - Botón JUGAR centrado debajo del nombre
 * - Bordes redondeados (12dp)
 *
 * BADGES:
 * -------
 * - HOT: Rojo (#E53935)
 * - NUEVO: Verde (#4CAF50)  
 * - JACKPOT: Dorado (AccentGold)
 *
 * =====================================================================================
 */

/**
 * Enum para los tipos de badge disponibles
 */
enum class SlotBadgeType {
    NONE,       // Sin badge
    HOT,        // Badge rojo "HOT"
    NUEVO,      // Badge verde "NUEVO"
    JACKPOT     // Badge dorado "JACKPOT"
}

@Composable
fun SlotGameCard(
    slotName: String,
    slotImage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeType: SlotBadgeType = SlotBadgeType.NONE
) {
    // Colores para los diferentes tipos de badge
    val badgeColor = when (badgeType) {
        SlotBadgeType.HOT -> Color(0xFFE53935)      // Rojo
        SlotBadgeType.NUEVO -> Color(0xFF4CAF50)   // Verde
        SlotBadgeType.JACKPOT -> AccentGold        // Dorado
        SlotBadgeType.NONE -> Color.Transparent
    }
    
    val badgeText = when (badgeType) {
        SlotBadgeType.HOT -> "HOT"
        SlotBadgeType.NUEVO -> "NUEVO"
        SlotBadgeType.JACKPOT -> "JACKPOT"
        SlotBadgeType.NONE -> ""
    }

    // Tarjeta principal
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF252525))
            .clickable { onClick() }
    ) {
        Column {
            // ===================================================================
            // IMAGEN DEL SLOT CON BADGE Y NOMBRE
            // ===================================================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                // Imagen del slot (ocupa todo el espacio)
                Image(
                    painter = painterResource(id = slotImage),
                    contentDescription = slotName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
                
                // Gradiente SUTIL en la parte inferior (solo para legibilidad)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        )
                )
                
                // Badge (si existe) - esquina superior derecha
                if (badgeType != SlotBadgeType.NONE) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .background(
                                color = badgeColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badgeText,
                            color = if (badgeType == SlotBadgeType.JACKPOT) Color.Black else Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }
                }
                
                // Nombre del slot (sobre la imagen, parte inferior)
                Text(
                    text = slotName,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
            
            // ===================================================================
            // BOTON JUGAR
            // ===================================================================
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFE8A030),  // Naranja más suave
                                    Color(0xFFD4942A)   // Dorado más cálido
                                )
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JUGAR",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins
                    )
                }
            }
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SlotGameCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SlotGameCard(
                slotName = "Neon Fortune",
                slotImage = R.drawable.slot_neon_fortune,
                onClick = {},
                modifier = Modifier.width(160.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SlotGameCardWithBadgePreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SlotGameCard(
                slotName = "Inferno Spin",
                slotImage = R.drawable.slot_inferno_fortunes,
                onClick = {},
                badgeType = SlotBadgeType.HOT,
                modifier = Modifier.width(160.dp)
            )
        }
    }
}
