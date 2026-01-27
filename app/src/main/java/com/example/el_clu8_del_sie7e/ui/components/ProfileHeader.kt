package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * PROFILEHEADER.KT - COMPONENTE DE CABECERA DEL PERFIL
 * =====================================================================================
 *
 * Este componente muestra la información principal del usuario:
 * - Foto de perfil circular con borde dorado
 * - Badge VIP (si aplica)
 * - Nombre del usuario
 * - Nivel/Rango (ej: "Nivel: Diamante")
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * ProfileHeader(
 *     userName = "Pingüino machetero",
 *     userLevel = "Diamante",
 *     isVip = true,
 *     profileImageRes = R.drawable.profile_placeholder
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun ProfileHeader(
    userName: String,
    userLevel: String,
    isVip: Boolean,
    profileImageRes: Int?, // Nullable para cuando el usuario agregue su imagen
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ------------------------------------------------------------------
        // FOTO DE PERFIL CON BORDE DORADO Y BADGE VIP
        // ------------------------------------------------------------------
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            // Foto de perfil
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                AccentGold,
                                Color(0xFFFFD700).copy(alpha = 0.7f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                if (profileImageRes != null) {
                    // Mostrar imagen del usuario
                    Image(
                        painter = painterResource(id = profileImageRes),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder cuando no hay imagen
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Sin foto de perfil",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            // Badge VIP
            if (isVip) {
                Box(
                    modifier = Modifier
                        .background(
                            color = PrimaryRed,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = AccentGold,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "VIP",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ------------------------------------------------------------------
        // NOMBRE DEL USUARIO
        // ------------------------------------------------------------------
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            color = AccentGold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ------------------------------------------------------------------
        // NIVEL/RANGO
        // ------------------------------------------------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Icono de diamante (placeholder - el usuario puede cambiar esto)
            Icon(
                painter = painterResource(id = R.drawable.ic_cards), // Placeholder
                contentDescription = "Nivel",
                tint = AccentGold,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "Nivel: $userLevel",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontSize = 11.sp
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun ProfileHeaderPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        ProfileHeader(
            userName = "Pingüino machetero",
            userLevel = "Diamante",
            isVip = true,
            profileImageRes = null // Sin imagen por ahora
        )
    }
}
