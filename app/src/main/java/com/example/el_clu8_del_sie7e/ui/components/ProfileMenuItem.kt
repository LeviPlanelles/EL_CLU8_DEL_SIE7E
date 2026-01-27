package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * PROFILEMENUITEM.KT - COMPONENTE DE ITEM DE MENU DEL PERFIL
 * =====================================================================================
 *
 * Este componente muestra una opcion del menu del perfil con:
 * - Icono a la izquierda
 * - Titulo principal
 * - Subtitulo descriptivo
 * - Flecha a la derecha indicando que es clickeable
 *
 * EJEMPLO DE USO:
 * ```kotlin
 * ProfileMenuItem(
 *     icon = Icons.Default.Person,
 *     title = "Datos Personales",
 *     subtitle = "Gestiona tu información",
 *     onClick = { /* navegar a datos personales */ }
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PrimaryRed.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.5.dp,
                color = PrimaryRed,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // ------------------------------------------------------------------
        // ICONO Y TEXTOS (LADO IZQUIERDO)
        // ------------------------------------------------------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Icono dentro de un círculo dorado
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentGold,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Textos (título y subtítulo)
            androidx.compose.foundation.layout.Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }

        // ------------------------------------------------------------------
        // FLECHA (LADO DERECHO)
        // ------------------------------------------------------------------
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Ir a $title",
            tint = AccentGold,
            modifier = Modifier.size(28.dp)
        )
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun ProfileMenuItemPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        ProfileMenuItem(
            icon = Icons.Default.Person,
            title = "Datos Personales",
            subtitle = "Gestiona tu información",
            onClick = {}
        )
    }
}
