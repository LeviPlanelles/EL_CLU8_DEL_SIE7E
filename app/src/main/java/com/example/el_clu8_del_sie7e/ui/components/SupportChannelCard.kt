package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * SUPPORTCHANNELCARD.KT - COMPONENTE DE TARJETA DE CANAL DE SOPORTE
 * =====================================================================================
 *
 * Este componente muestra un canal de atención al cliente:
 * - Icono del canal (chat o email)
 * - Título del canal
 * - Subtítulo con tiempo de respuesta o estado
 * - Badge "Online" (opcional)
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * SupportChannelCard(
 *     icon = Icons.Default.Chat,
 *     title = "Chat en Vivo",
 *     subtitle = "Espera: < 1 min",
 *     isOnline = true,
 *     backgroundColor = PrimaryRed,
 *     onClick = { /* abrir chat */ }
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun SupportChannelCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isOnline: Boolean = false,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.5.dp,
                color = AccentGold.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Column {
            // ------------------------------------------------------------------
            // FILA SUPERIOR: ICONO + BADGE ONLINE
            // ------------------------------------------------------------------
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Icono dentro de círculo con borde dorado
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = AccentGold,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = AccentGold,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Badge "Online" (solo si está online)
                if (isOnline) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF2E7D32).copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // Punto verde
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF4CAF50), CircleShape)
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                            Text(
                                text = "Online",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ------------------------------------------------------------------
            // TÍTULO
            // ------------------------------------------------------------------
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ------------------------------------------------------------------
            // SUBTÍTULO
            // ------------------------------------------------------------------
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SupportChannelCardPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SupportChannelCard(
                icon = Icons.Default.Email,
                title = "Chat en Vivo",
                subtitle = "Espera: < 1 min",
                isOnline = true,
                backgroundColor = PrimaryRed,
                onClick = {}
            )

            SupportChannelCard(
                icon = Icons.Default.Email,
                title = "Enviar Ticket",
                subtitle = "Respuesta en 24h",
                isOnline = false,
                backgroundColor = Color(0xFF3A3A3A),
                onClick = {}
            )
        }
    }
}
