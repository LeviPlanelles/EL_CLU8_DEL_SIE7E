package com.example.el_clu8_del_sie7e.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
 * SLOTFILTERCHIP.KT - COMPONENTE DE FILTRO PARA SLOTS
 * =====================================================================================
 *
 * Este componente muestra un chip de filtro para la pantalla de Slots.
 * Diseño fiel a la imagen de referencia.
 *
 * DISEÑO:
 * -------
 * - Estado seleccionado: fondo dorado/naranja con texto negro
 * - Estado no seleccionado: fondo transparente con borde dorado y texto dorado
 * - Icono opcional a la izquierda
 * - Esquinas redondeadas (20dp para efecto pill)
 *
 * =====================================================================================
 */

@Composable
fun SlotFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Int? = null
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) AccentGold else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .then(
                if (!selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = AccentGold.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp)
                    )
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono opcional (solo para "Todos")
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    tint = if (selected) Color.Black else AccentGold,
                    modifier = Modifier
                        .size(14.dp)
                        .padding(end = 4.dp)
                )
            }

            // Texto del filtro
            Text(
                text = text,
                color = if (selected) Color.Black else AccentGold,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

// ======================================================================================
// PREVIEW - VISTA PREVIA EN ANDROID STUDIO
// ======================================================================================
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun SlotFilterChipPreview() {
    EL_CLU8_DEL_SIE7ETheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SlotFilterChip(
                text = "Todos",
                selected = true,
                onClick = {},
                icon = R.drawable.ic_grid
            )
            SlotFilterChip(
                text = "Nuevos",
                selected = false,
                onClick = {}
            )
            SlotFilterChip(
                text = "Jackpot",
                selected = false,
                onClick = {}
            )
            SlotFilterChip(
                text = "Clásico",
                selected = false,
                onClick = {}
            )
        }
    }
}
