package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * FILTERCHIP.KT - COMPONENTE DE CHIP DE FILTRO
 * =====================================================================================
 *
 * Componente reutilizable para mostrar opciones de filtro en formato chip/badge.
 *
 * USOS:
 * -----
 * - Filtros de categorías en listas (ej: "Todos", "Depósitos", "Retiradas")
 * - Navegación entre secciones
 * - Tags/etiquetas seleccionables
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - Estado seleccionado/no seleccionado con diferentes estilos
 * - Icono opcional
 * - Borde dorado con transparencia
 * - Fondo rojo cuando está seleccionado
 * - Texto en negrita cuando está seleccionado
 *
 * DISEÑO:
 * -------
 * - Borde: Dorado con 60% opacidad
 * - Fondo seleccionado: Rojo primario (PrimaryRed)
 * - Fondo no seleccionado: Transparente
 * - Texto: Blanco
 * - Icono: Dorado (AccentGold)
 *
 * EJEMPLO DE USO:
 * ---------------
 * ```kotlin
 * FilterChip(
 *     text = "Depósitos",
 *     icon = Icons.Filled.CreditCard,
 *     isSelected = selectedFilter == "Depósitos",
 *     onClick = { onFilterSelected("Depósitos") }
 * )
 * ```
 *
 * =====================================================================================
 */
@Composable
fun FilterChip(
    text: String,
    icon: ImageVector?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ===================================================================
    // CONTENEDOR DEL CHIP
    // ===================================================================
    Box(
        modifier = modifier
            // Fondo: Rojo si está seleccionado, transparente si no
            .background(
                color = if (isSelected) PrimaryRed else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            // Borde dorado semitransparente
            .border(
                width = 1.dp,
                color = AccentGold.copy(alpha = 0.6f),
                shape = RoundedCornerShape(10.dp)
            )
            // Hacer clickable
            .clickable { onClick() }
            // Padding interno
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        // ===================================================================
        // CONTENIDO: ICONO (OPCIONAL) + TEXTO
        // ===================================================================
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mostrar icono solo si se proporciona
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }

            // Texto del chip
            Text(
                text = text,
                color = Color.White,
                fontSize = 13.sp,
                // Negrita si está seleccionado, normal si no
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
