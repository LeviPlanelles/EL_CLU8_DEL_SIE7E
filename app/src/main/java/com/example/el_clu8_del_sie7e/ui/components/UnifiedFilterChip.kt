package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el_clu8_del_sie7e.R
import com.example.el_clu8_del_sie7e.ui.theme.AccentGold
import com.example.el_clu8_del_sie7e.ui.theme.EL_CLU8_DEL_SIE7ETheme
import com.example.el_clu8_del_sie7e.ui.theme.PrimaryRed

/**
 * =====================================================================================
 * UNIFIEDFILTERCHIP.KT - COMPONENTE UNIFICADO DE CHIP DE FILTRO
 * =====================================================================================
 *
 * Componente reutilizable y UNIFICADO para mostrar opciones de filtro en formato chip.
 * Este componente reemplaza a FilterChip, FilterButton y SlotFilterChip para mantener
 * consistencia visual en toda la aplicación.
 *
 * USOS:
 * -----
 * - Filtros de categorías en listas (ej: "Todos", "Depósitos", "Retiradas")
 * - Filtros de juegos (ej: "Slots", "Cartas", "Mesa")
 * - Filtros de promociones (ej: "Todos", "Bonos", "Giros Gratis")
 * - Cualquier lugar donde se necesiten chips seleccionables
 *
 * CARACTERÍSTICAS:
 * ----------------
 * - Estilo CONSISTENTE en toda la app (8dp border radius)
 * - Soporte para iconos Material (ImageVector) o recursos drawable (Int)
 * - Estado seleccionado/no seleccionado con diferentes estilos
 * - Borde dorado siempre visible
 * - Fondo rojo cuando está seleccionado
 * - Texto y iconos cambian de color según estado
 *
 * DISEÑO UNIFICADO:
 * -----------------
 * - Border radius: 8dp (consistente)
 * - Altura fija: 40dp (evita cambios de tamaño al seleccionar)
 * - Borde: 1dp, AccentGold (siempre visible)
 * - Fondo seleccionado: PrimaryRed
 * - Fondo no seleccionado: Transparente
 * - Texto/Icono seleccionado: Blanco
 * - Texto/Icono no seleccionado: AccentGold
 * - Padding: horizontal 12dp, vertical 8dp
 * - Font size: 12sp
 * - Font weight: Medium (SIEMPRE - evita cambios de ancho del texto)
 *
 * EJEMPLO DE USO CON ICONO MATERIAL:
 * ----------------------------------
 * ```kotlin
 * UnifiedFilterChip(
 *     text = "Depósitos",
 *     isSelected = selectedFilter == "Depósitos",
 *     onClick = { onFilterSelected("Depósitos") },
 *     icon = Icons.Filled.CreditCard
 * )
 * ```
 *
 * EJEMPLO DE USO CON ICONO DRAWABLE:
 * ----------------------------------
 * ```kotlin
 * UnifiedFilterChip(
 *     text = "Slots",
 *     isSelected = selectedFilter == "Slots",
 *     onClick = { onFilterSelected("Slots") },
 *     iconRes = R.drawable.ic_slots
 * )
 * ```
 *
 * EJEMPLO DE USO SIN ICONO:
 * -------------------------
 * ```kotlin
 * UnifiedFilterChip(
 *     text = "Todos",
 *     isSelected = true,
 *     onClick = { }
 * )
 * ```
 *
 * =====================================================================================
 */

@Composable
fun UnifiedFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,           // Para iconos de Material Icons
    iconRes: Int? = null,                 // Para iconos de recursos drawable
    showIconOnlyWhenSelected: Boolean = false  // Mostrar icono solo cuando está seleccionado
) {
    // ===================================================================
    // COLORES SEGÚN ESTADO
    // ===================================================================
    val backgroundColor = if (isSelected) PrimaryRed else Color.Transparent
    val borderColor = AccentGold
    val contentColor = if (isSelected) Color.White else AccentGold
    
    // IMPORTANTE: Usamos FontWeight.Medium SIEMPRE para evitar que el texto
    // cambie de ancho cuando se selecciona/deselecciona el chip.
    // Esto previene el problema de que los chips cambien de tamaño.
    val fontWeight = FontWeight.Medium

    // ===================================================================
    // DETERMINAR SI MOSTRAR ICONO
    // ===================================================================
    val shouldShowIcon = when {
        icon == null && iconRes == null -> false  // No hay icono
        showIconOnlyWhenSelected -> isSelected    // Solo mostrar si está seleccionado
        else -> true                               // Siempre mostrar
    }

    // ===================================================================
    // CONTENEDOR DEL CHIP
    // Altura fija de 40dp para evitar cambios de tamaño
    // ===================================================================
    Box(
        modifier = modifier
            // ALTURA FIJA: Evita que el chip cambie de tamaño
            .height(40.dp)
            // Fondo: Rojo si está seleccionado, transparente si no
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            // Borde dorado (siempre visible)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            // Hacer clickable
            .clickable { onClick() }
            // Padding interno (reducido para caber mejor)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // ===================================================================
        // CONTENIDO: ICONO (OPCIONAL) + TEXTO
        // ===================================================================
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ---------------------------------------------------------------
            // ICONO (si corresponde mostrarlo)
            // ---------------------------------------------------------------
            if (shouldShowIcon) {
                // Priorizar ImageVector sobre drawable resource
                when {
                    icon != null -> {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    iconRes != null -> {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
                // Espacio entre icono y texto
                Spacer(modifier = Modifier.width(4.dp))
            }

            // ---------------------------------------------------------------
            // TEXTO DEL CHIP
            // maxLines = 1 y overflow para evitar que el texto se expanda
            // ---------------------------------------------------------------
            Text(
                text = text,
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = fontWeight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ======================================================================================
// PREVIEWS - VISTAS PREVIAS EN ANDROID STUDIO
// ======================================================================================

/**
 * Preview de chips básicos sin icono
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E, name = "Basic Chips")
@Composable
fun UnifiedFilterChipBasicPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UnifiedFilterChip(
                text = "Todos",
                isSelected = true,
                onClick = {}
            )
            UnifiedFilterChip(
                text = "Depósitos",
                isSelected = false,
                onClick = {}
            )
            UnifiedFilterChip(
                text = "Retiradas",
                isSelected = false,
                onClick = {}
            )
        }
    }
}

/**
 * Preview de chips con iconos Material
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E, name = "With Material Icons")
@Composable
fun UnifiedFilterChipWithMaterialIconsPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UnifiedFilterChip(
                text = "Todos",
                isSelected = true,
                onClick = {},
                icon = Icons.Filled.GridView
            )
            UnifiedFilterChip(
                text = "Filtrar",
                isSelected = false,
                onClick = {},
                icon = Icons.Filled.FilterList
            )
        }
    }
}

/**
 * Preview de chips con iconos drawable (simulado con Material icons)
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E, name = "With Drawable Icons")
@Composable
fun UnifiedFilterChipWithDrawableIconsPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Nota: En el preview real usarías R.drawable.xxx
            UnifiedFilterChip(
                text = "Slots",
                isSelected = true,
                onClick = {},
                icon = Icons.Filled.GridView // Placeholder para preview
            )
            UnifiedFilterChip(
                text = "Cartas",
                isSelected = false,
                onClick = {},
                icon = Icons.Filled.GridView // Placeholder para preview
            )
        }
    }
}

/**
 * Preview comparativo: seleccionado vs no seleccionado
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E, name = "Selected vs Unselected")
@Composable
fun UnifiedFilterChipStatesPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            UnifiedFilterChip(
                text = "Seleccionado",
                isSelected = true,
                onClick = {},
                icon = Icons.Filled.GridView
            )
            UnifiedFilterChip(
                text = "No Seleccionado",
                isSelected = false,
                onClick = {},
                icon = Icons.Filled.GridView
            )
        }
    }
}
